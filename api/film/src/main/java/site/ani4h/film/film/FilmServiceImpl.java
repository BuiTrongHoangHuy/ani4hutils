package site.ani4h.film.film;

import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import site.ani4h.film.film.entity.Film;
import site.ani4h.film.film.entity.FilmFilter;
import site.ani4h.film.film.entity.FilmList;
import site.ani4h.shared.common.Paging;
import site.ani4h.shared.gen.UserGrpc;
import site.ani4h.shared.gen.UserOuterClass;

import java.util.List;

@Component
public class FilmServiceImpl implements FilmService {
    private final FilmRepository filmRepository;
    @GrpcClient("user")
    private UserGrpc.UserFutureStub userStub;
    public FilmServiceImpl(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Override
    public List<FilmList> getFilms(Paging paging, FilmFilter filter) {
        var a = userStub.getUserById(UserOuterClass.GetUsersByIdReq.newBuilder().build());

        return filmRepository.getFilms(paging, filter);
    }

    @Override
    public Film getFilmById(int id) {
        return filmRepository.getFilmById(id);
    }

    @Override
    public List<Film> getTopFilmHot(Paging paging) {
        return filmRepository.getTopFilmHot(paging);
    }

}
