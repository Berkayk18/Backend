package nl.hu.inno.humc.monoliet.course.domain;

import jakarta.persistence.*;
import nl.hu.inno.humc.monoliet.course.domain.exceptions.DataAlreadyExistException;
import nl.hu.inno.humc.monoliet.course.domain.exceptions.LimitExceededException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Table(name="material")
public class CourseMaterial {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(cascade = ALL)
    @JoinColumn(name = "material_id")
    private List<Module> modules;

    private String subject;

    @Transient
    private static final int MAX_MATERIAL_LIMIT = 10;


    public CourseMaterial(String subject)
    {
        this.validateSubject(subject);
        this.modules = new ArrayList<>();
    }

    private void validateSubject(String subject) {
        if (subject.isEmpty())
        {
            throw new IllegalArgumentException("Subject cannot be empty");
        } else if(subject.length() > 100)
        {
            throw new IllegalArgumentException("Subject is too long");
        }
        this.subject = subject;
    }

    public void createNewModule(String name, Date deadline)
    {
        if (!this.checkCountAllowedModulesIsAchieved())
        {
            throw new LimitExceededException("Modules");
        }
        else if(hasReachedModuleLimit(name))
        {
            throw new DataAlreadyExistException("Module");
        }
        this.modules.add(new Module(name, deadline));
    }

    private boolean hasReachedModuleLimit(String name) {
        return this.modules.stream()
                .anyMatch(module -> module.getModuleContent()
                        .getName()
                        .equals(name));
    }

    private boolean checkCountAllowedModulesIsAchieved() {
        return this.modules.size() < MAX_MATERIAL_LIMIT;
    }

    protected CourseMaterial() {}

    public Long getId() {
        return id;
    }

    public String getSubject()
    {
        return this.subject;
    }

    public List<Module> getModules() {
        return this.modules;
    }
}
