package com.example.bmshop.service;

import com.example.bmshop.dto.CommentDTO;
import com.example.bmshop.entity.Comment;
import com.example.bmshop.repository.BasketRepository;
import com.example.bmshop.repository.CommentRespository;
import com.example.bmshop.repository.CustomerRepository;
import com.example.bmshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    CommentRespository commentRespository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    BasketRepository basketRepository;

    public List<Comment> getComments(){
        return commentRespository.findAll();
    }

    public Comment findCommentById(Long commentId) {
       return commentRespository.findById(commentId).orElseThrow(() -> new IllegalStateException("fds"));
    }

    public void getCommentForCustomer(Long customerId) {
        commentRespository.findAll().stream().filter(x -> x.equals(customerId));
    }


    //ADDING COMMENTS

    @Transactional
    public void addCommentToCustomer(Long customerId, CommentDTO comment_text){
        Comment comment = new Comment();
        comment.setCustomer(customerRepository.getOne(customerId));
        comment.setCommentText(comment_text.getCommentText());
        commentRespository.save(comment);
    }

    @Transactional
    public void addCommentToProduct(Long productId, CommentDTO comment_text){
        Comment comment = new Comment();
        comment.setProduct(productRepository.getOne(productId));
        comment.setCommentText(comment_text.getCommentText());
        commentRespository.save(comment);
    }

    @Transactional
    public void addCommentToBasket(Long basketId, CommentDTO comment_text){
        Comment comment = new Comment();
        comment.setBasket(basketRepository.getOne(basketId));
        comment.setCommentText(comment_text.getCommentText());
        commentRespository.save(comment);
    }


    // DELETE COMMENTS
    public void deleteCommentById(Long commentId){
        commentRespository.deleteById(commentId);
    }
    public void deleteCommentByProductId(Long productId){
        commentRespository.deleteById(productId);
    }
    public void deleteCommentByBasketId(Long basketId){
        commentRespository.deleteById(basketId);
    }

    public void deleteCommentByCustomerId(Long customerId){
        List<Comment> commentList = commentRespository
                .findAll()
                .stream()
                .filter(c -> c.getCustomer().getId().equals(customerId))
                .collect(Collectors.toList());

        commentRespository.deleteAll(commentList);
    }

    public void updateComment(Comment comment){
        comment.setCommentId(comment.getCommentId());
        if(comment.getCommentId() != null){
                    }
    }

}
