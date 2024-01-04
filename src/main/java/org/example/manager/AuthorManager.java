package org.example.manager;

import org.example.db.DBConnectionProvider;
import org.example.model.Author;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorManager {

    private Connection connection = DBConnectionProvider.getInstance().getConnection();

    public void add(Author author) {

//        String sql = "INSERT into author(name,surname,age,email)VALUES ('" + author.getName() + "','" + author.getSurname() + "'," + author.getAge() + ",'" + author.getEmail() + "')";
//        String query = "INSERT into author(name,surname,age,email)VALUES ('%s','%s',%d,'%s')";
//        String sql = String.format(query, author.getName(), author.getSurname(), author.getAge(), author.getEmail());
        String query = "INSERT into author(name,surname,age,email)VALUES (?,?,?,?)";
        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, author.getName());
            ps.setString(2, author.getSurname());
            ps.setInt(3, author.getAge());
            ps.setString(4, author.getEmail());
            ps.executeUpdate();
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                author.setId(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Author> getAllAuthors() {
        String sql = "SELECT * FROM author";
        List<Author> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                int age = resultSet.getInt("age");
                String email = resultSet.getString("email");
                Author author = new Author(id, name, surname, email, age);
                result.add(author);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Author getAuthorById(int id) {
        String sql = "SELECT * FROM author WHERE id =" + id;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                int age = resultSet.getInt("age");
                String email = resultSet.getString("email");
                return new Author(id, name, surname, email, age);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteAuthorById(int id) {
        if (getAuthorById(id) == null) {
            System.out.println("Author with id = " + id + "does not exists");
            return;
        }
        String sql = "DELETE FROM author WHERE id = " + id;
        try {

            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            System.out.println("author was removed!!!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateAuthor(Author author) {
        if (getAuthorById(author.getId()) == null) {
            System.out.println("Author with id = " + author.getId() + "does not exists");
            return;
        }

        String query = "UPDATE author SET name = '%s',surname= '%s', age = %d, email ='%s' WHERE id = %d";
        String sql = String.format(query, author.getName(), author.getSurname(), author.getAge(), author.getEmail(), author.getId());
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            System.out.println("Author updated!!!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
