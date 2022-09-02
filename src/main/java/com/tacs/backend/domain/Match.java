package com.tacs.backend.domain;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Data
@ToString
@RedisHash("Match")
public class Match implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String fecha;
    private String cancha;
    private String resultado;
    private String equipoVisitante;
    private String equipoLocal;


}
