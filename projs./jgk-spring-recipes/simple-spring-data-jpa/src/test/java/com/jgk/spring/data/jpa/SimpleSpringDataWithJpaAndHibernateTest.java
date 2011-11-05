package com.jgk.spring.data.jpa;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jgk.spring.data.jpa.domain.Book;
import com.jgk.spring.data.jpa.domain.Camera;
import com.jgk.spring.data.jpa.repository.BookRepository;
import com.jgk.spring.data.jpa.repository.CameraRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/SimpleSpringDataWithJpaAndHibernateTest-config.xml" })
@SuppressWarnings("serial")
public class SimpleSpringDataWithJpaAndHibernateTest {

    @Inject
    BookRepository bookRepository;

    @Inject
    CameraRepository cameraRepository;

    @Test
    public void testCamera() {
        List<Camera> cameras = new ArrayList<Camera>() {
            {
                add(Camera.createCamera("Nikon", "SLR"));
                add(Camera.createCamera("Kodak", "110"));
                add(Camera.createCamera("Canon", "30D"));
            }
        };

        for (Camera camera : cameras) {
            cameraRepository.save(camera);
            System.out.println(camera.getId());
        }

        List<Camera> camerasRestored = cameraRepository.findAll();
        List<Camera> camerasOrdered = cameraRepository.findAllOrdered();
        assertEquals(cameras.size(), camerasRestored.size());
        assertEquals(cameras.size(), camerasOrdered.size());
     }

    @Test
    public void testBook() {
        List<Book> books = new ArrayList<Book>() {
            {
                add(Book.createBook("Moby Dick", "Melville"));
                add(Book.createBook("Of Mice and Men", "Steinbeck"));
            }
        };

        for (Book book : books) {
            bookRepository.save(book);
            System.out.println(book.getId());
        }

        List<Book> booksRetrieved = bookRepository.findAll();
        assertEquals(books.size(), booksRetrieved.size());
    }
}
