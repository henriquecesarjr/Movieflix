package com.devsuperior.movieflix.services;

import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.repositories.GenreRepository;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

    @Autowired
    private GenreRepository genreRepository;

    @Transactional(readOnly = true)
    public MovieDetailsDTO findById(Long id) {
        Movie movie = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Filme não encontrado")
        );

        return new MovieDetailsDTO(movie);
    }

    @Transactional(readOnly = true)
    public Page<MovieCardDTO> findByGenre(Long genreId, Pageable pageable) {

        if (genreId == null) {
            Page<Movie> result = repository.findAll(pageable);
            return result.map(MovieCardDTO::new);
        }

        Page<Movie> result = repository.findByGenre(genreId, pageable);
        return result.map(MovieCardDTO::new);
    }

    @Transactional(readOnly = true)
    public List<ReviewDTO> findReviews(Long id) {
        List<Review> result = repository.findReviews(id);
        return result.stream().map(ReviewDTO::new).toList();
    }

}
