package co.com.pragma.r2dbc.helper;

import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.lang.reflect.ParameterizedType;
import java.util.function.Function;

public abstract class ReactiveAdapterOperations<E, D, I, R extends ReactiveCrudRepository<D, I> & ReactiveQueryByExampleExecutor<D>> {
    protected R repository;
    protected ObjectMapper mapper;
    private final Class<D> dataClass;
    private final Function<D, E> toEntityFn;

    @SuppressWarnings("unchecked")
    protected ReactiveAdapterOperations(R repository, ObjectMapper mapper, Function<D, E> toEntityFn) {
        this.repository = repository;
        this.mapper = mapper;
        ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.dataClass = (Class<D>) genericSuperclass.getActualTypeArguments()[1];
        this.toEntityFn = toEntityFn;
    }

    protected D toEntity(E data) {
        return mapper.map(data, dataClass);
    }

    protected E toData(D entity) {
        return entity != null ? toEntityFn.apply(entity) : null;
    }

}
