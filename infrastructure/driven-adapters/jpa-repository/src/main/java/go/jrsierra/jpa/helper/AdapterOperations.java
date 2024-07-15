package go.jrsierra.jpa.helper;

import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


public abstract class AdapterOperations<E, D, I, R extends CrudRepository<E, I> & QueryByExampleExecutor<E>> {
    protected R repository;
    private final Class<D> dataClass;
    private final Class<E> entityClass;
    protected ObjectMapper mapper;
    private final Function<D, E> toEntityFn;

    @SuppressWarnings("unchecked")
    protected AdapterOperations(R repository, ObjectMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.dataClass = (Class<D>) genericSuperclass.getActualTypeArguments()[1];
        this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[0];
        this.toEntityFn = d -> mapper.map(d, entityClass);
    }

    protected D toData(E entity) {
        return mapper.map(entity, dataClass);
    }

    protected List<D> toData(List<E> entityList) {
        return Optional.ofNullable(entityList).orElse(Collections.emptyList()).stream()
                .map(objectToMapper ->  this.mapper.map(objectToMapper, dataClass))
                .collect(Collectors.toList());
    }

    protected E toEntity(D data) {
        return data != null ? toEntityFn.apply(data) : null;
    }

    public Mono<D> save(D data) {
        return Mono.just(data)
                .map(this::toEntity)
                .flatMap(this::saveData)
                .map(this::toData);
    }

    public Flux<D> saveAll(List<D> entities) {
        return Flux.fromIterable(entities)
                .map(this::toEntity)
                .collectList()
                .flatMapMany(this::saveData)
                .map(this::toData);
    }

    protected Mono<E> saveData(E data) {
        return Mono.just(repository.save(data));
    }

    protected Flux<E> saveData(List<E> data) {
        return Flux.fromIterable(repository.saveAll(data));
    }

    public Mono<D> findById(I id) {
        return Mono.justOrEmpty(repository.findById(id))
                .map(this::toData);
    }

    public Flux<D> findAll(){
        return Flux.fromIterable(repository.findAll())
                .map(this::toData);
    }
}