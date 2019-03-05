package com.simbirsoft.kondratyev.ruslan.pizzeria.models;

import lombok.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pair<A, B> implements Serializable {
    private A first;
    private B second;
}
