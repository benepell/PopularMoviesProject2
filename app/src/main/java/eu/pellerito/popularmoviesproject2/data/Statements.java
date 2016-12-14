package eu.pellerito.popularmoviesproject2.data;

class Statements {

    private static final String START_STATEMENTS = " CREATE TABLE IF NOT EXISTS ";

    public static final String FAVORITES_STATEMENT = START_STATEMENTS + Contract.Favorites.TABLE_NAME + " (" +
            Contract.Favorites.COLUMN_ID + " INTEGER PRIMARY KEY , " +
            Contract.Favorites.COLUMN_TITLE + " TEXT, " +
            Contract.Favorites.COLUMN_POSTER + " TEXT, " +
            Contract.Favorites.COLUMN_SYNOPSIS + " TEXT, " +
            Contract.Favorites.COLUMN_USER_RATING + " REAL NOT NULL, " +
            Contract.Favorites.COLUMN_RELEASE_DATE + " TEXT " +
            " );";
}
