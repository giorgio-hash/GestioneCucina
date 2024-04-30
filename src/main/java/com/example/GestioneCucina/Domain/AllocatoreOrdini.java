package com.example.GestioneCucina.Domain;

<<<<<<< Updated upstream
import org.springframework.stereotype.Service;

@Service
public class AllocatoreOrdini implements BackSignalPort{
    private final CodeIF gestioneCode;

    public AllocatoreOrdini(CodeIF gestioneCode) {
        this.gestioneCode = gestioneCode;
=======
import com.example.GestioneCucina.Domain.Entity.ClienteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class AllocatoreOrdini implements TestPort {
    private final FrontSignalPort frontSignalPort;

    @Autowired
    public AllocatoreOrdini(FrontSignalPort frontSignalPort) {
        this.frontSignalPort = frontSignalPort;
    }

    @Override
    public Iterable<ClienteEntity> getClienti() {
        return frontSignalPort.getClienti();
>>>>>>> Stashed changes
    }
}
