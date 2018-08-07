# Quiz-app
package com.kg.library.Controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.kg.library.Properties;
import com.kg.library.Repository.BookCategoryRepository;
import com.kg.library.Repository.BookRequestRepository;
import com.kg.library.Repository.BookreviewRepository;
import com.kg.library.Repository.LikereviewRepository;
import com.kg.library.model.BookCategory;
import com.kg.library.model.BookRequest;
import com.kg.library.model.Bookreview;
import com.kg.library.model.Likereview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/books")
public class BookRequestController {
        @Autowired
        private BookCategoryRepository bookCategoryRepository;
        @Autowired
        private BookRequestRepository bookRequestRepository;

        @Autowired
        private BookreviewRepository bookreviewRepository;
        @Autowired
        private LikereviewRepository likereviewRepository;

        @Autowired
        private Properties Libraryprop1;

        @RequestMapping(value = "/userwise/{userId}", method = RequestMethod.GET)
        public void userwise(@PathVariable Long userId) {
                List<BookRequest> lstBookRequest2 = bookRequestRepository.findAll();
                List<BookRequest> lstBookRequest1 = bookRequestRepository.findByUserid(userId);
                List<Bookreview> reviewlist = bookreviewRepository.findAll();
                List<Likereview> likelist = likereviewRepository.findAll();

                lstBookRequest1.stream().forEach(System.out::println);
                System.out.println("*********************");

                long books = lstBookRequest1.stream().count();
                System.out.println("No. of  books:" + books);

                Map<String, Long> strmap = lstBookRequest1.stream()
                                .collect(Collectors.groupingBy(BookRequest::getCategory, Collectors.counting()));
                System.out.println("Category wise count:" + strmap);

                Map<Object, Object> collect = strmap.entrySet().stream().filter(map -> map.getValue() > 1)
                                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
                System.out.println("More than one book taken from Category:" + collect);

                Map<String, Long> distinctcat = strmap.entrySet().stream().filter(map -> map.getValue() == 1)
                                .collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
                System.out.println("Distinct Category:" + distinctcat);

                System.out.println("**********POINT********");
                Long list = strmap.entrySet().stream().filter(map -> map.getValue() >= 2).map(x -> x.getKey())
                                .collect(Collectors.counting());
                Long list1 = strmap.entrySet().stream().collect(Collectors.counting());

                System.out.println("the points for books is :" + books * Libraryprop1.getpointsperbook());
                System.out.println("the point for each catergory :" + list1 * Libraryprop1.getpointspercategory());
                System.out.println("catergory points for more than 2 books in single cat "
                                + list * Libraryprop1.getmorethan5());

                List<Long> reviewbook = reviewlist.stream().filter(x -> x.getUserid() == userId)
                                .map(x -> x.getBookrevid()).collect(Collectors.toList());

                List<Long> likebook = likelist.stream().map(x -> x.getBookrewid()).filter(reviewbook::contains)
                                .distinct().collect(Collectors.toList());

                Long reviewpoint = reviewbook.stream().collect(Collectors.counting());
                System.out.println("the reviewpoint:" + reviewpoint * Libraryprop1.getReviewpints());

                Long likepoint = likebook.stream().collect(Collectors.counting());
                System.out.println("the liked point :" + likepoint * Libraryprop1.getLikedpoints());

                Long likedbookstaken = lstBookRequest2.stream().map(x -> x.getBookreqid()).filter(reviewbook::contains)
                                .collect(Collectors.counting());
                System.out.println("the points for liked book took by other users: "
                                + likedbookstaken * Libraryprop1.getTakenlikedbooks());

        }

        @RequestMapping(value = "/", method = RequestMethod.GET)
        List<BookCategory> bookcategory() {
                return bookCategoryRepository.findAll();
        }
        @RequestMapping(value = "/bookRequest", method = RequestMethod.GET)
  ResponseEntity<List<BookRequest>> bookRequest() {
                return new ResponseEntity<List<BookRequest>> ( bookRequestRepository.findAll(), HttpStatus.OK);
             
        }

        @RequestMapping(value = "/bookReview", method = RequestMethod.GET)
        List<Bookreview> bookReview() {
                return bookreviewRepository.findAll();
        }

        @RequestMapping(value = "/likeReview", method = RequestMethod.GET)
        List<Likereview> likeReview() {
                return likereviewRepository.findAll();
        }

}



1  Book1inCat1 Cat1 D 1
3  Book1inCat2 Cat2 D 1
5  Book1inCat3 Cat3 D 1
6  Book2inCat3 Cat3 D 1
*********************
No. of  books:4
Category wise count:{Cat3=2, Cat2=1, Cat1=1}
More than one book taken from Category:{Cat3=2}
Distinct Category:{Cat2=1, Cat1=1}
**********POINT********
the points for books is :4
the point for each catergory :3
catergory points for more than 2 books in single cat 3
the reviewpoint:10
the liked point :2
the points for liked book took by other users: 6



spring.datasource.url =jdbc:h2:file:~/library

spring.h2.console.enabled=true
spring.datasource.username = sa
spring.datasource.password = sa
spring.datasource.driverClassName = org.h2.Driver
server.port=9090

library.books.pointsperbook=1
library.books.pointspercategory=1
library.books.morethan5=3
library.books.reviewpints=5
library.books.likedpoints=1
library.books.takenlikedbooks=3
# @Value(3)
# private Long point;


-- SELECT * FROM BOOK_REQUEST;
-- BOOKREQID  	BOOK  	CATEGORY  	STATUS  	USERID  

insert into BOOK_REQUEST values (1,'Book1inCat1','Cat1','D',1);
insert into BOOK_REQUEST values (2,'Book2inCat1','Cat1','D',2);
insert into BOOK_REQUEST values (3,'Book1inCat2','Cat2','D',1);
insert into BOOK_REQUEST values (4,'Book1inCat1','Cat1','D',2);
insert into BOOK_REQUEST values (5,'Book1inCat3','Cat3','D',1);
insert into BOOK_REQUEST values (6,'Book2inCat3','Cat3','D',1);
insert into BOOK_REQUEST values (7,'Book1inCat1','Cat1','D',3);
insert into BOOK_REQUEST values (8,'Book1inCat2','Cat2','D',4);

-- SELECT * FROM BOOKREVIEW;
-- BOOKREVID  	BOOKID  	REVIEW  	USERID  

insert into BOOKREVIEW values (1,1,'Good',1);
insert into BOOKREVIEW values (2,2,'Good',1);
insert into BOOKREVIEW values (3,1,'Good',2);
insert into BOOKREVIEW values (4,2,'Good',2);
insert into BOOKREVIEW values (5,1,'Good',3);
insert into BOOKREVIEW values (6,4,'Good',4);
insert into BOOKREVIEW values (7,5,'Good',6);
insert into BOOKREVIEW values (8,7,'Good',7);

-- SELECT * FROM LIKEREVIEW;
-- LIKEID  	LIKESTATUS  	BOOKREWID  	COMMENTS  	USERID  	VOTE  

insert into LIKEREVIEW values (1,'liked',1,'OK',2,'U');
insert into LIKEREVIEW values (2,'liked',1,'OK',3,'U');
insert into LIKEREVIEW values (3,'liked',2,'OK',2,'U');
insert into LIKEREVIEW values (4,'liked',3,'OK',5,'U');
insert into LIKEREVIEW values (5,'liked',2,'OK',6,'U');
insert into LIKEREVIEW values (6,'liked',4,'OK',7,'U');

-- BOOK_ID  	BOOKNAME  	BOOKCAT_ID  

-- insert into BOOK  values (1,'harrypotter',4);
-- insert into BOOK  values (2,'alchemist',3);
-- insert into BOOK  values (3,'gonegal',1);
-- insert into BOOK  values (4,'2states',6);
-- insert into BOOK  values (5,'The Monk',3);
-- insert into BOOK  values (6,'Twilight',5);
-- insert into BOOK  values (7,'revolution2020',1);


-- BOOKCAT_ID  	NAME 
insert into BOOK_CATEGORY  values (1,'Cat1');
insert into BOOK_CATEGORY  values (2,'Cat2');
insert into BOOK_CATEGORY  values (3,'Cat3');
insert into BOOK_CATEGORY  values (4,'Cat4');
insert into BOOK_CATEGORY  values (5,'Cat5');
insert into BOOK_CATEGORY  values (6,'Cat6');
insert into BOOK_CATEGORY  values (7,'Cat7');


    
