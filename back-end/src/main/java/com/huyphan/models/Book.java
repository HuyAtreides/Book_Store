package com.huyphan.models;

import java.time.LocalDate;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/** Represents a book. */
@Getter
@Setter
@Entity
@Table(name = "Book")
public class Book {

	/** Book ISBN. */
	@Column(name = "ISBN", nullable = true)
	private String isbn;

	/** Uniquely identify a book. */
	@Id
	@Column(name = "Id", nullable = false)
	private int id;

	/** Book title. */
	@Column(name = "Title", nullable = false)
	private String title;

	/** Book price. */
	@Column(name = "Price", nullable = false)
	private double price;

	/** Book published date. */
	@Column(name = "Published", columnDefinition = "Date", nullable = false)
	private LocalDate published;

	/** Book pages number. */
	@Column(name = "Pages", nullable = false)
	private int pages;

	/** Publisher of this book. */
	@Column(name = "Publisher", nullable = false)
	private String publisher;

	/** Book cover image url. */
	@Column(name = "Image_URL", nullable = false)
	private String imageUrl;

	/** Book description. */
	@Column(name = "Description", nullable = false)
	private String description;


	/** Author of this book. */
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "Author_Id")
	private Author author;

	/** Number of genres of this book. */
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, mappedBy = "book")
	private Set<BookGenre> genres;
}
