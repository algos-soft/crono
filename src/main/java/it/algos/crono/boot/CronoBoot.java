package it.algos.crono.boot;

import it.algos.vbase.boot.BaseBoot;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Project wiki24
 * Created by Algos
 * User: gac
 * Date: Thu, 16-Nov-2023
 * Time: 13:55
 */
@Service
@Component("cronoBoot")
public class CronoBoot extends BaseBoot {


    public CronoBoot() {
    }

    /**
     * Controllo del resetStartup <br>
     * Chiamato da BaseBoot ad avvio programma (ContextRefreshedEvent) <br>
     * Esegue solo se il flag usaCheckResetStartup=true (default false) <br>
     * Esegue il metodo xxxService.checkResetStartup() di ogni modulo dell'applicazione <br>
     */
    @Override
    protected void checkResetStartup() {
        super.checkResetStartup();
    }
}



