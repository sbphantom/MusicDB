package musicdb;

import java.util.Scanner;

public class CollectionManager {
    private final Date CURRENTDate = Date.todayDate();
    private final Date MINArtistDate = new Date(1899, 11, 31);


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

    /**
     * commandHandler() calls the following method based on the users command.
     *
     * @param command        command entered from user.
     * @param inputString    inputstring entered from user.
     * @param mainCollection collection that stores all Albums
     * @return corresponding String from command function called.
     */
    private String commandHandler(String command, String inputString, Collection mainCollection) {
        switch (command) {
            case "A":
                return commandA(inputString.split(","), mainCollection);
            case "D":
                return commandD(inputString.split(","), mainCollection);
            case "R":
                return commandR(inputString.split(","), mainCollection);
            case "PD":
                if(mainCollection.isEmpty()){ return "Collection is empty!"; }
                mainCollection.printByDate();
            case "PG":
                if(mainCollection.isEmpty()){ return "Collection is empty!"; }
                mainCollection.printByGenre();
            case "PR":
                if(mainCollection.isEmpty()){ return "Collection is empty!"; }
                mainCollection.printByRating();
            default:
                return "Invalid Command";

        }
    }

    private String rateAlbum(Album album, int rating) {
        album.rate(rating);
        return String.format("You rate %d for %s:%s(%s)", rating, album.getTitle(), album.getReleased().toString(), album.getArtist().getArtistName());
    }

    private String commandR(String[] inputString, Collection maincollection) {
        String title = inputString[1];
        String artistName = inputString[2];
        Date date = dateBuilder(inputString[3].split("/"));
        int rating = Integer.parseInt(inputString[4]);
        Album album = maincollection.searchAlbum("TABD", title, artistName, date);


        if (rating < 1 || rating > 5) {
            return "Invalid rating, rating scale is 1 to 5";
        }
        else if(album == null){
              return String.format("%s(%s:%S) is not in the collection", title, artistName, date);
        }

        return rateAlbum(album, rating);
    }


    /**
     * deleteAlbum() is helper method used  to delete the album
     * in the collection  from the given params
     *
     * @param title          album title
     * @param artist         artist name
     * @param date           date of the album
     * @param mainCollection collection storing all the albums
     */
    private void deleteAlbum(String title, String artist, String date, Collection mainCollection) {
        Date dateEntered = dateBuilder(date.split("/"));
        Album targetAlbum = mainCollection.searchAlbum("TARD", title, artist, dateEntered);
        mainCollection.remove(targetAlbum);
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
        Album targetAlbum = mainCollection.searchAlbum("TARD", title, artist, dateEntered);

        // However, the album being removed might not exist in the collection.
        if (mainCollection.isEmpty() || targetAlbum == null) {
            return String.format("%s(%s:%S) is not in the collection", title, artist, date);
        }

        mainCollection.remove(targetAlbum);
        return String.format("%s(%s:%s) removed from the collection", title, artist, date);
    }

    /**
     * albumBuilder() builds an Album from the user input
     * @param param
     * @returns Album
     */
    private Album albumBuilder(String[] param) {
        String title = param[1];
        String artist = param[2];
        Date artistBirth = dateBuilder(param[3].split("/"));
        Date releaseDate = dateBuilder(param[5].split("/"));
        Genre musicGenre = genreBuilder(param[4]);
        Artist artistObj = new Artist(artist, artistBirth);
        Album album = new Album(title, artistObj, musicGenre, releaseDate);

        return album;
    }

    /**
     * genreBuilder assigns the correct enum from the user input,
     *
     * @param genreEntry string input from the user.
     * @return genre
     */
    private Genre genreBuilder(String genreEntry) {
        Genre musicGenre = switch (genreEntry.toUpperCase()) {
            case "POP" -> Genre.POP;
            case "COUNTRY" -> Genre.COUNTRY;
            case "CLASSICAL" -> Genre.CLASSICAL;
            case "JAZZ" -> Genre.JAZZ;
            default -> Genre.UNKNOWN;
        };
        return musicGenre;
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
            return "Artist DOB:" + param[3] + " is invalid";
        }

        //Case: The release date is not a valid calendar date.
        //Case: The release date is today or a future date.
        //Case: The release date is before the year of 1900.
        Date releaseDate = album.getReleased();
        if (releaseDate.toString().equals("0/0/0") ||
                releaseDate.equals(CURRENTDate) ||
                releaseDate.compareTo(CURRENTDate) > 0
        ) {
            return "Date Released: " + param[5]+ " is invalid";
        }

        //An album with the same title and artist is already in the collection.
        if (mainCollection.contains(album)) {
            return String.format("%s(%s:%s) is already added in the collection", album.getTitle(), album.getArtist(), album.getArtist().getArtistBorn());
        }

        return "";
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
        }
        else if(mainCollection.contains(album)){
            return String.format("%s(%s:%s) is already added to the collection.", album.getTitle(), album.getArtist(), input[3]);

        }

        mainCollection.add(album); 
        return String.format("%s(%s:%s) added to the collection.", album.getTitle(), album.getArtist(), album.getArtist().getArtistBorn());
    }


    public void run() {
        System.out.println("Collection Manager is up and running\n");
        Scanner input = new Scanner(System.in);
        Collection mainCollection = new Collection();
        while (input.hasNext()) {

            String line = input.nextLine();
            if(line.trim().isEmpty()){
                continue;
            }
            System.out.println(commandHandler(commandSelect(line), line, mainCollection));


            if (line.equals("Q")) {
                System.out.println("Collection Manager terminated");
                input.close();
                break;
            }
        }


    }
}
