package nl.hu.inno.humc.monoliet.course.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import nl.hu.inno.humc.monoliet.course.domain.embeddables.ModuleContent;

import java.util.Date;

@Entity
public class Module {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private ModuleContent moduleContent;

    public Module(String name, Date deadline) {
      this.moduleContent = new ModuleContent(name, deadline);
    }

    protected Module() {}

    protected Long getId() {
        return id;
    }

    public ModuleContent getModuleContent()
    {
        return this.moduleContent;
    }
}
