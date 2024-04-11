package perriAlessandro.U5w2D4.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import perriAlessandro.U5w2D4.entities.BlogPost;
import perriAlessandro.U5w2D4.payloads.BlogPostPayload;
import perriAlessandro.U5w2D4.services.BlogPostService;

import java.util.UUID;

@RestController
@RequestMapping("/blogPost")
public class BlogPostController {

    @Autowired
    private BlogPostService blogPostService;


    // GET .../blogPost
    @GetMapping
    private Page<BlogPost> getAllBlogPost(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size,
                                          @RequestParam(defaultValue = "id") String sortBy) {
        return blogPostService.getBlogPostList(page, size, sortBy);
    }

    // POST .../blogPost (+ body)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Status Code 201
    private BlogPost saveBlogPost(@RequestBody BlogPostPayload body) {
        return blogPostService.saveBlogPost(body);
    }

    // GET .../blogPost/{postId}
    @GetMapping("/{blogId}")
    private BlogPost findBlogById(@PathVariable UUID blogId) {
        return blogPostService.findById(blogId);
    }

    // PUT .../blogPost/{postId} (+ body)
    @PutMapping("/{blogId}")
    private BlogPost findBlogByIdAndUpdate(@PathVariable UUID blogId, @RequestBody BlogPostPayload body) {
        return blogPostService.findByIdAndUpdate(blogId, body);
    }

    // DELETE .../blogPost/{postId}
    @DeleteMapping("/{blogId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Status Code 204
    private void findByBlogIdAndDelete(@PathVariable UUID blogId) {
        blogPostService.findByIdAndDelete(blogId);
    }

}
