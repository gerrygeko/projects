package model;

import lombok.*;

import java.util.ArrayList;

@Getter
@RequiredArgsConstructor
public class Agenda {

    @Setter
    private ArrayList<WorkingDay> listWorkingDay = new ArrayList<>();
    private final Person person;

}
