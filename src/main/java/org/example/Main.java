package org.example;

import org.example.db.DBConnectionProvider;
import org.example.manager.AuthorManager;
import org.example.model.Author;

import java.util.List;

public class Main {

    private static AuthorManager authorManager = new AuthorManager();

    public static void main(String[] args) {

//        Author author = new Author("martiros","martirosyan","martiros@mail.ru",21);
//
//        authorManager.add(author);
//        System.out.println(author);
//        List<Author> allAuthors = authorManager.getAllAuthors();
//        for (Author allAuthor : allAuthors) {
//            System.out.println(allAuthor);
//        }
        // delete
//        System.out.println(authorManager.getAuthorById(3));
//        authorManager.deleteAuthorById(3);
//        System.out.println(authorManager.getAuthorById(3));

        //update
                Author author = new Author(1,"asdd","ffff","rgt@mail.ru",33);
                authorManager.updateAuthor(author);


    }
}