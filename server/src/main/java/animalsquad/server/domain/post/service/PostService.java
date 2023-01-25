package animalsquad.server.domain.post.service;

import animalsquad.server.domain.pet.entity.Pet;
import animalsquad.server.domain.pet.service.PetService;
import animalsquad.server.domain.post.entity.Post;
import animalsquad.server.domain.post.entity.PostImage;
import animalsquad.server.domain.post.repository.PostRepository;
import animalsquad.server.global.exception.BusinessLogicException;
import animalsquad.server.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PetService petService;
    private final PostImageService postImageService;

    public Post createPost(Post post, List<MultipartFile> files,long id) throws IllegalAccessException {
        long petId = post.getPet().getId();

        if(petId != id) {
            throw new BusinessLogicException(ExceptionCode.TOKEN_AND_ID_NOT_MATCH);
        }

        Pet pet = petService.findPet(petId);

        post.setPet(pet);
        post.setTitle(post.getTitle());
        post.setContents(post.getContents());

        for (MultipartFile file : files) {
            if(file != null && !file.isEmpty()) {
                postImageService.uploadImage(post, file);
            }
        }

        return postRepository.save(post);
    }

//    public Post updatePost(Post post, long postId) {
//        Post findPost = findVerifiedPost(post.getId());
//
//        Optional.ofNullable(post.getId())
//                .ifPresent(name -> findPost.setId(name));
//        Optional.ofNullable(post.getTitle())
//                .ifPresent(title -> findPost.setTitle(title));
//        Optional.ofNullable(post.getContents())
//                .ifPresent(content -> findPost.setContents(content));
//
//        Post savedPost = postRepository.save(findPost);
//
//        return savedPost;
//    }
//
//    public Post findPost(long postId) {
//        return findVerifiedPost(postId);
//    }
//
//    public List<Post> findPosts() {
//        return null;
//    }
//
//    public void deletePost(long postId) {
//        post
//
//    }
}
