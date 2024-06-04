package com.devsuperior.movieflix.repositories;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query(value = """
        SELECT obj FROM Movie obj
        WHERE obj.genre.id = :genreId
        ORDER BY obj.title
    """)
    Page<Movie> findByGenre(Long genreId, Pageable pageable);

    @Query(value = """
        SELECT obj.reviews FROM Movie obj
        WHERE obj.id = :id
    """)
    List<Review> findReviews(Long id);

    @Query(nativeQuery = true, value = """
        SELECT * FROM tb_movie
        ORDER BY title
    """)
    Page<Movie> findAll(Pageable pageable);

}
