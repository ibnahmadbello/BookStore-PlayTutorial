package controllers;

import java.util.Set;
import play.*;
import play.mvc.*;
// import play.api.data.*;
import play.data.*;
import play.data.FormFactory;
import views.html.booksView.*;
import javax.inject.Inject;
import java.util.List;

import models.*;

public class BooksController extends Controller{

  @Inject
  FormFactory formFactory;

  // for all book
  public Result index(){
    // Set<Book> books = Book.allBooks();
    List<Book> books = Book.find.all();

    return ok(index.render(books));
  }

  // to create a book
  public Result create(){
    Form<Book> bookForm = formFactory.form(Book.class);
    return ok(create.render(bookForm));
  }

  // to save a book
  public Result save(){
    Form<Book> bookForm = formFactory.form(Book.class).bindFromRequest();
    Book book = bookForm.get();
    // Book.add(book);
    book.save();
    return redirect(routes.BooksController.index());
  }

  public Result edit(Integer id){
    Book book = Book.find.byId(id);
    if (book == null) {
      return notFound("Book not found.");
    }
    Form<Book> bookForm = formFactory.form(Book.class).fill(book);
    return ok(edit.render(bookForm));
  }

  public Result update(){
    Book book = formFactory.form(Book.class).bindFromRequest().get();
    Book oldBook = Book.find.byId(book.id);
    if (oldBook == null) {
      return notFound("Book Not Found.");
    } else{
      oldBook.title = book.title;
      oldBook.price = book.price;
      oldBook.author = book.author;

      oldBook.update();
      return redirect(routes.BooksController.index());
    }

  }

  public Result destroy(Integer id){
    Book book = Book.find.byId(id);
    if (book == null) {
      return notFound("Book not Found.");
    }
    // Book.remove(book);
    book.delete();
    return redirect(routes.BooksController.index());
  }

  // for book details
  public Result show(Integer id){
    Book book = Book.find.byId(id);
    if (book == null) {
      return notFound("Book not Found");
    }
    return ok(show.render(book));
  }
}
