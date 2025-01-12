package ru.ezhidkova.jdbc_spring_example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.ezhidkova.jdbc_spring_example.model.Book;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository

public class BookRepositoryImpl implements BookRepository {

    public BookRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Autowired
    private DataSource dataSource;

    @Override
    public List<Book> findAllBooks() {
        List<Book> result = new ArrayList<>();
        String SQL_findAllBooks = "Select * from books;";

        try (Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL_findAllBooks)) {
            while (resultSet.next()){
                Book book = convertRowToBook(resultSet);
                result.add(book);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return result;
    }

    @Override
    public Book findBookById(Long id) {
        String SQL_findBookById = "SELECT * FROM books WHERE id = " + id;
        Book book = null;

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_findBookById)) {
            if (resultSet.next()) {
                book = convertRowToBook(resultSet);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }

        return book;
    }


    private Book convertRowToBook(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        return new Book(id, name);
    }
}
