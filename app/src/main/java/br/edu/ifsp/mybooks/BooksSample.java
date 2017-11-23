package br.edu.ifsp.mybooks;

import android.util.Log;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.NumberFormat;

public class BooksSample {

    /**
     * Be sure to specify the name of your application. If the application name is {@code null} or
     * blank, the application will log a warning. Suggested format is "MyCompany-ProductName/1.0".
     */
    private static final String APPLICATION_NAME = "br.ifsp.edu";

    private static final NumberFormat CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance();
    private static final NumberFormat PERCENT_FORMATTER = NumberFormat.getPercentInstance();

    private static void queryGoogleBooks(JsonFactory jsonFactory, String query) throws Exception {
        ClientCredentials.errorIfNotSpecified();

        Log.v("book", "entrou");

        // Set up Books client.
        final Books books = new Books.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, null)
                .setApplicationName(APPLICATION_NAME)
                .setGoogleClientRequestInitializer(new BooksRequestInitializer(ClientCredentials.API_KEY))
                .build();
        // Set query string and filter only Google eBooks.
        Log.v("Book","Query: [" + query + "]");
        Books.Volumes.List volumesList = books.volumes().list(query);
        volumesList.setFilter("ebooks");

        // Execute the query.
        Volumes volumes = volumesList.execute();
        if (volumes.getTotalItems() == 0 || volumes.getItems() == null) {
            Log.v("Book","No matches found.");
            return;
        }

        // Output results.
        for (Volume volume : volumes.getItems()) {
            Volume.VolumeInfo volumeInfo = volume.getVolumeInfo();
            Volume.SaleInfo saleInfo = volume.getSaleInfo();
            Log.v("Book","==========");
            // Title.
            Log.v("Book","Title: " + volumeInfo.getTitle());
            // Author(s).
            java.util.List<String> authors = volumeInfo.getAuthors();
            if (authors != null && !authors.isEmpty()) {
                Log.v("Book","Author(s): ");
                for (int i = 0; i < authors.size(); ++i) {
                    Log.v("Book",authors.get(i));
                    if (i < authors.size() - 1) {
                        Log.v("Book",", ");
                    }
                }
                System.out.println();
            }
            // Description (if any).
            if (volumeInfo.getDescription() != null && volumeInfo.getDescription().length() > 0) {
                Log.v("Book","Description: " + volumeInfo.getDescription());
            }
            // Ratings (if any).
            if (volumeInfo.getRatingsCount() != null && volumeInfo.getRatingsCount() > 0) {
                int fullRating = (int) Math.round(volumeInfo.getAverageRating().doubleValue());
                Log.v("Book","User Rating: ");
                for (int i = 0; i < fullRating; ++i) {
                    Log.v("Book","*");
                }
                Log.v("Book"," (" + volumeInfo.getRatingsCount() + " rating(s))");
            }
            // Price (if any).
            if (saleInfo != null && "FOR_SALE".equals(saleInfo.getSaleability())) {
                double save = saleInfo.getListPrice().getAmount() - saleInfo.getRetailPrice().getAmount();
                if (save > 0.0) {
                    Log.v("Book","List: " + CURRENCY_FORMATTER.format(saleInfo.getListPrice().getAmount())
                            + "  ");
                }
                Log.v("Book","Google eBooks Price: "
                        + CURRENCY_FORMATTER.format(saleInfo.getRetailPrice().getAmount()));
                if (save > 0.0) {
                    Log.v("Book","  You Save: " + CURRENCY_FORMATTER.format(save) + " ("
                            + PERCENT_FORMATTER.format(save / saleInfo.getListPrice().getAmount()) + ")");
                }
                System.out.println();
            }
            // Access status.
            String accessViewStatus = volume.getAccessInfo().getAccessViewStatus();
            String message = "Additional information about this book is available from Google eBooks at:";
            if ("FULL_PUBLIC_DOMAIN".equals(accessViewStatus)) {
                message = "This public domain book is available for free from Google eBooks at:";
            } else if ("SAMPLE".equals(accessViewStatus)) {
                message = "A preview of this book is available from Google eBooks at:";
            }
            System.out.println(message);
            // Link to Google eBooks.
            System.out.println(volumeInfo.getInfoLink());
        }
        System.out.println("==========");
        System.out.println(
                volumes.getTotalItems() + " total results at http://books.google.com/ebooks?q="
                        + URLEncoder.encode(query, "UTF-8"));
    }

    public static void test(String isbn) {
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        try {
            // Parse command line parameters into a query.
            // Query format: "[<author|isbn|intitle>:]<query>"
            String prefix = null;
            String query = "";

            isbn = "--isbn " + isbn;

            if (prefix != null) {
                query = prefix + query;
            }
            try {
                queryGoogleBooks(jsonFactory, query);
                Log.v("Book","success");
                // Success!
                return;
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
