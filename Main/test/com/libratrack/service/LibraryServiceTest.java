package com.libratrack.service;

import com.libratrack.model.Book;
import com.libratrack.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LibraryServiceTest {

    private LibraryService library;
    private Student student;
    private Book book;

    @BeforeEach
    void setUp() {
        library = new LibraryServiceImpl();

        student = new Student(201, "Ebrima Saine");

        book = new Book(
                "978-0435905255",
                "Things Fall Apart",
                "Chinua Achebe",
                true
        );

        library.addBook(book);
    }

    // =========================
    // SEARCH TESTS
    // =========================

    @Test
    void shouldFindBookByTitle() {
        Book result = library.findBookByTitle("Things Fall Apart");

        assertNotNull(result);
        assertEquals("Things Fall Apart", result.getTitle());
    }

    @Test
    void shouldSearchBookByAuthor() {
        var results = library.searchBooks("Chinua Achebe");

        assertEquals(1, results.size());
        assertEquals("Things Fall Apart", results.get(0).getTitle());
    }

    @Test
    void shouldReturnEmptyResultsForUnknownBook() {
        var results = library.searchBooks("Unknown Book");

        assertTrue(results.isEmpty());
    }

    // =========================
    // CHECKOUT / BORROW TESTS
    // =========================

    @Test
    void shouldBorrowAvailableBook() {
        boolean result = library.borrowBook(
                "Things Fall Apart",
                student
        );

        assertTrue(result);
        assertFalse(book.isAvailable());
        assertEquals(1, student.getBorrowHistory().size());
    }

    @Test
    void shouldNotBorrowAlreadyBorrowedBook() {
        library.borrowBook("Things Fall Apart", student);

        Student anotherStudent = new Student(202, "Another Student");

        boolean result = library.borrowBook(
                "Things Fall Apart",
                anotherStudent
        );

        assertFalse(result);
        assertEquals(0, anotherStudent.getBorrowHistory().size());
    }

    @Test
    void shouldNotBorrowBookThatDoesNotExist() {
        boolean result = library.borrowBook(
                "Unknown Book",
                student
        );

        assertFalse(result);
    }

    // =========================
    // RESERVATION TESTS
    // =========================

    @Test
    void shouldAddMemberToReservationWaitlist() {
        library.borrowBook("Things Fall Apart", student);

        Student waitingStudent = new Student(202, "Waiting Student");

        library.reserveBook(
                "978-0435905255",
                waitingStudent
        );

        MemberCheck.assertSameMember(
                waitingStudent,
                library.getNextReservation("978-0435905255")
        );
    }

    @Test
    void shouldServeReservationsInOrder() {
        library.borrowBook("Things Fall Apart", student);

        Student firstStudent = new Student(202, "First Student");
        Student secondStudent = new Student(203, "Second Student");

        library.reserveBook("978-0435905255", firstStudent);
        library.reserveBook("978-0435905255", secondStudent);

        assertSame(
                firstStudent,
                library.serveNextReservation("978-0435905255")
        );

        assertSame(
                secondStudent,
                library.getNextReservation("978-0435905255")
        );
    }

    @Test
    void shouldReturnNullWhenReservationWaitlistIsEmpty() {
        assertNull(
                library.getNextReservation("978-0435905255")
        );
    }

    private static class MemberCheck {
        static void assertSameMember(
                com.libratrack.model.Member expected,
                com.libratrack.model.Member actual
        ) {
            assertSame(expected, actual);
        }
    }
}