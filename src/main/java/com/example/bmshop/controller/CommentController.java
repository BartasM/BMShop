package com.example.bmshop.controller;

import com.example.bmshop.dto.CommentDTO;
import com.example.bmshop.service.CommentService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping
    public void GetComments() {
        commentService.getComments();
    }

    @GetMapping(path = "{commentId}")
    public void getCommentByid(@PathVariable("commentId") Long commentId) throws NotFoundException {
        commentService.findCommentById(commentId);
    }

    @GetMapping(path = "customer/{customerId}")
    public void getCommentForCustomer(@PathVariable("customerId") Long customerId) {
        commentService.getCommentForCustomer(customerId);
    }

    //Adding comment to Customer, product or basket

    @PostMapping(path = "customer/{customerId}")
    public void addCommentToCustomer(
                    @PathVariable("customerId")
                    Long customerId,
                    @RequestBody CommentDTO commentText) {
        commentService.addCommentToCustomer(customerId, commentText);
    }

    @PostMapping(path = "product/{productId}")
    public void addCommentToProduct(
            @PathVariable("productId")
                    Long productId,
            @RequestBody CommentDTO commentText) {
        commentService.addCommentToCustomer(productId, commentText);
    }

    @PostMapping(path = "basket/{basketId}")
    public void addCommentToBasket(
            @PathVariable("basketId")
                    Long basketId,
            @RequestBody CommentDTO commentText) {
        commentService.addCommentToCustomer(basketId, commentText);
    }

    // Delete comment by id customer, product or basket

    @DeleteMapping(path = "customer/{customerId}")
    public void deleteCommentByCustomerId(@PathVariable Long customerId){
        commentService.deleteCommentByCustomerId(customerId);
    }

}
