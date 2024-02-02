package musicdb;

import java.util.Scanner;

public class CollectionManager {
    private final Date CURRENTDate = Date.todayDate(); 
    private final Date MINArtistDate = new Date(1899,11,31);



    private String commandSelect(String line){
        //Declare what method is being used from the user. 
        String[] input = line.split(","); 
        String[] commands = {"A", "D", "R", "PD","PG", "PR"};

        for(String command : commands){
            if(input[0].equals(command)){
                return command; 
            }
        }
        return ""; 
    }

    
    private String commandHandler(String command, String inputString, Collection mainCollection){
        switch (command) {
            case "A":
              
                return commandA( inputString.split(','), mainCollection);
            case "D": 

            case "R": 

            case "PD": 

            case "PG": 

            case "PR": 
            default:
                return "Invalid Command";

        }
    } 


    private String commandD(String[] inputString, Collection mainCollection){
            String title = inputString[1]; 
            String artist = inputString[2]; 
            String date = inputString[3]; 

            // However, the album being removed might not exist in the collection.
            if(mainCollection.isEmpty()){
                return String.format("%s(%s:%S) is not in the collection", title, artist, date); 
            }

    }

    private void deleteAlbum(String title, String artist, String date, Collection mainCollection){
        
       Date dateEntered =  dateBuilder(date.split("/")); 
        mainCollection.findAlbum(title, artist, dateEntered); 
    }





    private Album albumBuilder(String[] param){
        String title = param[1]; 
        String artist = param[2]; 
        Date artistBirth = dateBuilder(param[3].split("/")); 
        Date releaseDate = dateBuilder(param[5].split("/")); 
        Genre musicGenre = genreBuilder(param[4]); 
        Artist artistObj = new Artist(artist, artistBirth); 
        Album album = new Album(title, artistObj, musicGenre, releaseDate); 

        return album; 
    }
    
    private Genre genreBuilder(String genreEntry){
        Genre musicGenre; 
        switch(genreEntry.toUpperCase()){
            case "POP":
            musicGenre = Genre.POP; 
            break;
        case "COUNTRY":
            musicGenre = Genre.COUNTRY; 
            break;
        case "CLASSICAL":
            musicGenre = Genre.CLASSICAL; 
            break;
        case "JAZZ":
            musicGenre = Genre.JAZZ; 
            break;
        default:
            musicGenre = Genre.UNKNOWN; 
            break;
        }
        return musicGenre; 
    }

        
    private Date dateBuilder(String[] dateEntry){
        int[] result = new int[dateEntry.length];
        for (int i = 0; i < dateEntry.length; i++) {
            result[i] = Integer.parseInt(dateEntry[i]);
        }
       
        Date date = new Date(result[2], result[0], result[1]); 
        return date; 
    }


    private String albumErrorChecker(Album album, Collection mainCollection){
        
        //Case: The date of birth of an artist is not a valid calendar date.
        //Case: The date of birth of an artist is today or a future date.
        //Case: The date of birth of an artist is before the year of 1900.
        Date artistBirth = album.getArtist().getArtistBorn(); 
        if(artistBirth.toString().equals("0/0/0") || 
        artistBirth.equals(CURRENTDate) ||  
        artistBirth.compareTo(CURRENTDate) > 0){
            return "Artist DOB:" + album.getArtist().getArtistBorn().toString() + "is invalid"; 
        }
                     
        //Case: The release date is not a valid calendar date.
        //Case: The release date is today or a future date.
        //Case: The release date is before the year of 1900.
        Date releaseDate = album.getReleased(); 
        if(releaseDate.toString().equals("0/0/0") || 
           releaseDate.equals(CURRENTDate) ||
           releaseDate.compareTo(CURRENTDate) > 0
          ){
            return "Date Released: "  + album.getReleased().toString() + " is invalid";
        }

        //An album with the same title and artist is already in the collection.
        if(mainCollection.contains(album)){
            return String.format("%s(%s:%s) is already added in the collection", album.getTitle(), album.getArtist(), album.getArtist().getArtistBorn()); 
        }

        return ""; 
    }   
    
    private String commandA(String[] input, Collection mainCollection){
        Album album = albumBuilder(input); 
        String results = albumErrorChecker(album, mainCollection); 
        if(!results.isEmpty()){
            return results; 
        }

        return String.format("%s(%s:%s) added to the collection.", album.getTitle(), album.getArtist(), album.getArtist().getArtistBorn()); 
    }



    public void run(){
        System.out.println("Collection Manager is up and running\n"); 
        Scanner input = new Scanner(System.in); 
        Collection mainCollection = new Collection(); 
        while(input.hasNext()){   
            
            String line = input.nextLine(); 
            System.out.println(commandHandler(commandSelect(line),line, mainCollection));
  
            if (line.equals("Q")){
                System.out.println("Collection Manager terminated"); 
                input.close();
                break; 
            }
        }



    }
}
