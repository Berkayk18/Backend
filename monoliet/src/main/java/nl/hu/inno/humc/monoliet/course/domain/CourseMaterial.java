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
    private List<Module> moduleList;

    private String subject;

    @Transient
    private static final int MAX_MATERIAL_LIMIT = 10;


    public CourseMaterial(String subject)
    {
        this.validateSubject(subject);
        this.moduleList = new ArrayList<>();
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
        else if(checkIfModuleWithGivenNameAlreadyExist(name))
        {
            throw new DataAlreadyExistException("Module");
        }
        this.moduleList.add(new Module(name, deadline));
    }

    private boolean checkIfModuleWithGivenNameAlreadyExist(String name) {
        return this.moduleList.stream()
                .anyMatch(module -> module.getModuleContent()
                        .getName()
                        .equals(name));
    }

    private boolean checkCountAllowedModulesIsAchieved() {
        return this.moduleList.size() < MAX_MATERIAL_LIMIT;
    }

    protected CourseMaterial() {}


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    protected void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getSubject()
    {
        return this.subject;
    }

    public List<Module> getModules() {
        return this.moduleList;
    }

    protected void setModule(List<Module> moduleList)
    {
        this.moduleList = moduleList;
    }
}
