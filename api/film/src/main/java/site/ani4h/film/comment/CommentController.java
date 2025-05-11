package site.ani4h.film.comment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.ani4h.film.comment.entity.CommentRequest;
import site.ani4h.film.comment.entity.CommentResponse;
import site.ani4h.shared.common.Uid;

import java.util.List;

@RestController
@RequestMapping("/v1/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<Void> addComment(@RequestBody CommentRequest request) {
        commentService.addComment(request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(
            @RequestBody CommentRequest request,
            @PathVariable int commentId) {
        commentService.updateComment(request, commentId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable int commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/film/{filmId}")
    public ResponseEntity<List<CommentResponse>> getCommentsByFilmId(@PathVariable Uid filmId) {
        List<CommentResponse> comments = commentService.getCommentsByFilmId(filmId.getLocalId());
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/episode/{episodeId}")
    public ResponseEntity<List<CommentResponse>> getCommentsByEpisodeId(@PathVariable Uid episodeId) {
        List<CommentResponse> comments = commentService.getCommentsByEpisodeId(episodeId.getLocalId());
        return ResponseEntity.ok(comments);
    }

}