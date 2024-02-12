/**
 * Insert Text
 * Insert Text
 *
 * @author Danny Onuorah, Adeola Asimolowo
 */

package musicdb;

import java.util.Scanner;

public class CollectionManager {
    private final Date CURRENTDate = Date.todayDate();

    /**
     * albumBuilder() builds an Album from the user input
     *
     * @param param fields to construct album
     * @return Album newly constructed album
     */
    private Album albumBuilder(String[] param) {
        String title = param[1];
        String artist = param[2];
        Date artistBirth = dateBuilder(param[3].split("/"));
        Date releaseDate = dateBuilder(param[5].split("/"));
        Genre musicGenre = genreBuilder(param[4]);
        Artist artistObj = new Artist(artist, artistBirth);

        return new Album(title, artistObj, musicGenre, releaseDate);
    }

    /**
     * genreBuilder assigns the correct enum from the user input,
     *
     * @param genreEntry string input from the user.
     * @return genre
     */
    private Genre genreBuilder(String genreEntry) {
        return switch (genreEntry.toUpperCase()) {
            case "POP" -> Genre.POP;
            case "COUNTRY" -> Genre.COUNTRY;
            case "CLASSICAL" -> Genre.CLASSICAL;
            case "JAZZ" -> Genre.JAZZ;
            default -> Genre.UNKNOWN;
        };
    }

    /**
     * dateBuilder() is used to build a date object from a date entered from the user.
     *
     * @param dateEntry date entered from user.
     * @return date object .
     */
    private Date dateBuilder(String[] dateEntry) {
        int[] result = new int[dateEntry.length];
        for (int i = 0; i < dateEntry.length; i++) {
            result[i] = Integer.parseInt(dateEntry[i]);
        }
        return new Date(result[2], result[0], result[1]);
    }

    /**
     * albumErrorChecker() is used to check for various errors that could occur.
     * Errors are listed in method description.
     *
     * @param album          album entered from user
     * @param mainCollection collection that stores all albums
     * @return s corresponding error message, if an error is found.
     */
    private String albumErrorChecker(Album album, Collection mainCollection, String[] param) {

        //Case: The date of birth of an artist is not a valid calendar date.
        //Case: The date of birth of an artist is today or a future date.
        //Case: The date of birth of an artist is before the year of 1900.
        Date artistBirth = album.getArtist().getArtistBorn();
        if (artistBirth.toString().equals("0/0/0") ||
                artistBirth.equals(CURRENTDate) ||
                artistBirth.compareTo(CURRENTDate) > 0) {
            return "Artist DOB: " + param[3] + " is invalid.";
        }

        //Case: The release date is not a valid calendar date.
        //Case: The release date is today or a future date.
        //Case: The release date is before the year of 1900.
        Date releaseDate = album.getReleased();
        if (releaseDate.toString().equals("0/0/0") ||
                releaseDate.equals(CURRENTDate) ||
                releaseDate.compareTo(CURRENTDate) > 0
        ) {
            return "Date Released: " + param[5] + " is invalid.";
        }

        //An album with the same title and artist is already in the collection.
        if (mainCollection.contains(album)) {
            return String.format("%s(%s) is already in the collection.", album.getTitle(), album.getArtist());
        }

        return "";
    }

    /**
     * reads the first command from a line in the terminal
     *
     * @param line input from terminal
     * @return the command at the beginning of the string.
     */
    private String commandSelect(String line) {
        //Declare what method is being used from the user.
        String[] input = line.split(",");
        String[] commands = {"A", "D", "R", "PD", "PG", "PR"};

        for (String command : commands) {
            if (input[0].equals(command)) {
                return command;
            }
        }
        return "";
    }


    private String rateAlbum(Album album, int rating) {
        album.rate(rating);
        return String.format("You rate %d for %s:%s(%s)", rating, album.getTitle(), album.getReleased().toString(), album.getArtist().getArtistName());
    }

    /**
     * commandA() is used to execute the A command, which adds an album to the collection
     *
     * @param input          input string from the user
     * @param mainCollection collection holding all the albums
     * @return returns the result of commandA()
     * (1. Album was addeed to the collection) (2. Album was already added) (3. Date entered is invalid)
     */
    private String commandA(String[] input, Collection mainCollection) {
        Album album = albumBuilder(input);
        String results = albumErrorChecker(album, mainCollection, input);
        if (!results.isEmpty()) {
            return results;
        } else if (mainCollection.contains(album)) {
            return String.format("%s(%s) is already added to the collection.", album.getTitle(), album.getArtist());
        }

        mainCollection.add(album);
        return String.format("%s(%s) added to the collection.", album.getTitle(), album.getArtist());
    }

    /**
     * returns the result of the R command, which rates an album
     *
     * @param inputString    input string from terminal
     * @param maincollection collection storing all the albums
     * @return string
     */
    private String commandR(String[] inputString, Collection maincollection) {
        String title = inputString[1];
        String artistName = inputString[2];
        Date date = dateBuilder(inputString[3].split("/"));
        int rating = Integer.parseInt(inputString[4]);
        Album album = maincollection.searchAlbum("TABD", title, artistName, date);

        if (rating < 1 || rating > 5) {
            return "Invalid rating, rating scale is 1 to 5.";
        } else if (album == null) {
            return String.format("%s(%s:%S) is not in the collection", title, artistName, date);
        }

        return rateAlbum(album, rating);
    }

    /**
     * commandD() is used to execute the delete command
     * which removes an album from the collection.
     *
     * @param inputString    the input command from the user
     * @param mainCollection Collection storing all the albums
     * @return string containing the result of the delete command.
     * (1. Album was removed) or (2. Album was not found)
     */
    private String commandD(String[] inputString, Collection mainCollection) {
        String title = inputString[1];
        String artist = inputString[2];
        String date = inputString[3];

        Date dateEntered = dateBuilder(date.split("/"));
        Album targetAlbum = mainCollection.searchAlbum("TABD", title, artist, dateEntered);

        // However, the album being removed might not exist in the collection.
        if (mainCollection.isEmpty() || targetAlbum == null) {
            return String.format("%s(%s:%S) is not in the collection", title, artist, date);
        }

        mainCollection.remove(targetAlbum);
        return String.format("%s(%s:%s) removed from the collection.", title, artist, date);
    }


    /**
     * commandHandler() calls the following method based on the users command.
     *
     * @param command        command entered from user.
     * @param inputString    inputstring entered from user.
     * @param mainCollection collection that stores all Albums
     * @return corresponding String from command function called.
     */
    private String commandHandler(String command, String inputString, Collection mainCollection) {
        return switch (command) {
            case "A" -> commandA(inputString.split(","), mainCollection);
            case "D" -> commandD(inputString.split(","), mainCollection);
            case "R" -> commandR(inputString.split(","), mainCollection);
            case "PD" -> {
                if (mainCollection.isEmpty()) yield "Collection is empty!";
                else yield mainCollection.printByDate();
            }
            case "PG" -> {
                if (mainCollection.isEmpty()) yield "Collection is empty!";
                else yield mainCollection.printByGenre();
            }
            case "PR" -> {
                if (mainCollection.isEmpty()) yield "Collection is empty!";
                else yield mainCollection.printByRating();
            }
            default -> "Invalid command!";
        };
    }


    /**
     * Runs the process for
     */
    public void run() {
        System.out.println("Collection Manager is up running.\n");
        Scanner input = new Scanner(System.in);
        Collection mainCollection = new Collection();
        while (input.hasNext()) {

            String line = input.nextLine();
            if (line.trim().isEmpty()) {
                continue;
            }

            if (line.equals("Q")) {
                System.out.println("Collection Manager terminated.");
                input.close();
                break;
            }

            System.out.println(commandHandler(commandSelect(line), line, mainCollection));

        }


    }
}