package com.bemobi.repository;

import com.bemobi.entity.Url;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UrlRepository extends CrudRepository<Url, Long>{
    public Url findById(Long id);
    public Url findByUrl(String url);
    public Url findByAlias(String alias);

    public List<Url> findAllByOrderById();
    public List<Url> findAllByOrderByIdDesc();
    public List<Url> findAllByOrderByUrl();
    public List<Url> findAllByOrderByUrlDesc();

}
