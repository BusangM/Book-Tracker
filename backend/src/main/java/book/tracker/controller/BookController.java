package book.tracker.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:3000")
public class BookController {

    private List<Map<String, Object>> mockBooks = new ArrayList<>();
    private Long nextId = 1L;

    public BookController() {
        initializeMockData();
    }

    /**
     * GET /api/books/{id} - Get a book by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getBookById(@PathVariable Long id) {
        Optional<Map<String, Object>> book = mockBooks.stream()
                .filter(b -> Objects.equals(b.get("id"), id))
                .findFirst();

        if (book.isPresent()) {
            return ResponseEntity.ok(book.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * POST /api/books - Add a new book
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createBook(@RequestBody Map<String, Object> bookData) {
        // Validate required fields
        if (!bookData.containsKey("title") || !bookData.containsKey("author")) {
            return ResponseEntity.badRequest().build();
        }

        // Create new book with auto-generated ID
        Map<String, Object> newBook = new HashMap<>();
        newBook.put("id", nextId++);
        newBook.put("title", bookData.get("title"));
        newBook.put("author", bookData.get("author"));
        newBook.put("publishedDate", bookData.get("publishedDate"));
        newBook.put("genre", bookData.get("genre"));

        mockBooks.add(newBook);

        return ResponseEntity.status(HttpStatus.CREATED).body(newBook);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateBook(
            @PathVariable Long id,
            @RequestBody Map<String, Object> bookData) {

        Optional<Map<String, Object>> existingBook = mockBooks.stream()
                .filter(b -> Objects.equals(b.get("id"), id))
                .findFirst();

        if (existingBook.isPresent()) {
            Map<String, Object> book = existingBook.get();

            // Update fields if provided
            if (bookData.containsKey("title")) {
                book.put("title", bookData.get("title"));
            }
            if (bookData.containsKey("author")) {
                book.put("author", bookData.get("author"));
            }
            if (bookData.containsKey("publishedDate")) {
                book.put("publishedDate", bookData.get("publishedDate"));
            }
            if (bookData.containsKey("genre")) {
                book.put("genre", bookData.get("genre"));
            }

            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE /api/books/{id} - Delete a book
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        boolean removed = mockBooks.removeIf(b -> Objects.equals(b.get("id"), id));

        if (removed) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    private void initializeMockData() {
        addMockBook("The Great Gatsby", "F. Scott Fitzgerald", "1925-04-10", "Classic Literature");
        addMockBook("To Kill a Mockingbird", "Harper Lee", "1960-07-11", "Fiction");
        addMockBook("1984", "George Orwell", "1949-06-08", "Dystopian Fiction");
        addMockBook("Pride and Prejudice", "Jane Austen", "1813-01-28", "Romance");
        addMockBook("The Catcher in the Rye", "J.D. Salinger", "1951-07-16", "Coming-of-age Fiction");
    }

    private void addMockBook(String title, String author, String publishedDate, String genre) {
        Map<String, Object> book = new HashMap<>();
        book.put("id", nextId++);
        book.put("title", title);
        book.put("author", author);
        book.put("publishedDate", publishedDate);
        book.put("genre", genre);
        mockBooks.add(book);
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllBooks() {
        return ResponseEntity.ok(mockBooks);
    }
}
