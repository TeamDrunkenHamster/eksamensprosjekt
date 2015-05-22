package view;

import logic.DatabaseBuilder;
import logic.DatabaseBuilderImpl;

public class App {
	public static void main(String[] args) {
		DatabaseBuilder databaseBuilder = new DatabaseBuilderImpl();
		databaseBuilder.createDatabase();
		new FFSFrame();
	}
}
