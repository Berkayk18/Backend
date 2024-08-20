package nl.hu.inno.humc.courseplanning.application.port;

import nl.hu.inno.humc.courseplanning.application.dto.NewStudentDTO;
import nl.hu.inno.humc.courseplanning.application.dto.RegisterStudentDTO;

public interface OsirisEventsConsumer {

    void receiveNewStudent(NewStudentDTO newStudent);

    void receiveStudentRegister(RegisterStudentDTO registerStudentDTO);

    void receiveStudentDeregister(RegisterStudentDTO registerStudentDTO);
}
