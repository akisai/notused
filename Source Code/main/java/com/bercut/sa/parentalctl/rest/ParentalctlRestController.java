package com.bercut.sa.parentalctl.rest;

import com.bercut.sa.parentalctl.db.DbException;
import com.bercut.sa.parentalctl.db.DbService;
import com.bercut.sa.parentalctl.db.GetMsisdnResponse;
import com.bercut.sa.parentalctl.logs.LoggerText;
import com.bercut.sa.parentalctl.rest.model.Children;
import com.bercut.sa.parentalctl.rest.model.Flags;
import com.bercut.sa.parentalctl.rest.model.Msisdn;
import com.bercut.sa.parentalctl.utils.Utils;
import com.bercut.sa.parentalctl.utils.ValidateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RestController
@RequestMapping("/v1")
@EnableWebMvc
public class ParentalctlRestController {

    private DbService dbService;

    @Autowired
    public ParentalctlRestController(DbService dbService) {
        this.dbService = dbService;
    }

    private final Logger logger = LoggerFactory.getLogger(ParentalctlRestController.class);

    @PostMapping("/parent")
    public ResponseEntity addParent(@RequestBody Msisdn msisdn) {
        long startExec = System.currentTimeMillis();
        final String sessionId = Utils.createUuid();
        HttpStatus status = HttpStatus.CREATED;
        if (logger.isDebugEnabled()) {
            logger.debug(LoggerText.REST_REQUEST.getText(), RestProcedure.add_parent, sessionId, msisdn);
        }
        try {
            Utils.validateMsisdn(msisdn.getMsisdn());
            dbService.addParent(sessionId, msisdn);
            long endExec = System.currentTimeMillis();
            if (logger.isDebugEnabled()) {
                logger.debug(LoggerText.SQL_RESPONSE.getText(), sessionId, RestProcedure.add_parent, endExec - startExec);
            }
        } catch (DbException e) {
            logger.error(LoggerText.DB_ERROR.getText(), sessionId, RestProcedure.add_parent, e.getMessage());
            status = Utils.parseError(e);
            logger.error(LoggerText.REST_ERROR.getText(), sessionId, RestProcedure.add_parent, status);
        } catch (ValidateException e) {
            logger.error(LoggerText.VALIDATE_ERROR.getText(), sessionId, RestProcedure.add_parent, e.getMessage());
            status = HttpStatus.BAD_REQUEST;
            logger.error(LoggerText.REST_ERROR.getText(), sessionId, RestProcedure.add_parent, status);
        }
        return new ResponseEntity(status);
    }

    @PostMapping("/child")
    public ResponseEntity addChild(@RequestBody Children children) {
        long startExec = System.currentTimeMillis();
        final String sessionId = Utils.createUuid();
        HttpStatus status = HttpStatus.CREATED;
        if (logger.isDebugEnabled()) {
            logger.debug(LoggerText.REST_REQUEST.getText(), RestProcedure.add_child, sessionId, children.toString());
        }
        try {
            Utils.validateMsisdn(children.getMsisdn(), children.getParent());
            if (children.getMsisdn().equals(children.getParent())) {
                throw new ValidateException("Equals msisdn");
            }
            Utils.validateFlags(children.getFwdAoc(), children.getFwdPay());
            dbService.addChild(sessionId, children);
            long endExec = System.currentTimeMillis();
            if (logger.isDebugEnabled()) {
                logger.debug(LoggerText.SQL_RESPONSE.getText(), sessionId, RestProcedure.add_child, endExec - startExec);
            }
        } catch (DbException e) {
            logger.error(LoggerText.DB_ERROR.getText(), sessionId, RestProcedure.add_child, e.getMessage());
            status = Utils.parseError(e);
            logger.error(LoggerText.REST_ERROR.getText(), sessionId, RestProcedure.add_child, status);
        } catch (ValidateException e) {
            logger.error(LoggerText.VALIDATE_ERROR.getText(), sessionId, RestProcedure.add_child, e.getMessage());
            status = HttpStatus.BAD_REQUEST;
            logger.error(LoggerText.REST_ERROR.getText(), sessionId, RestProcedure.add_child, status);
        }
        return new ResponseEntity(status);
    }

    @DeleteMapping("/parent/{msisdn:\\w+}")
    public ResponseEntity delParent(@PathVariable String msisdn) {
        long startExec = System.currentTimeMillis();
        final String sessionId = Utils.createUuid();
        HttpStatus status = HttpStatus.NO_CONTENT;
        if (logger.isDebugEnabled()) {
            logger.debug(LoggerText.REST_REQUEST.getText(), RestProcedure.delete_parent, sessionId, "msisdn=" + msisdn);
        }
        try {
            Utils.validateMsisdn(msisdn);
            dbService.delParent(sessionId, msisdn);
            long endExec = System.currentTimeMillis();
            if (logger.isDebugEnabled()) {
                logger.debug(LoggerText.SQL_RESPONSE.getText(), sessionId, RestProcedure.delete_parent, endExec - startExec);
            }
        } catch (DbException e) {
            logger.error(LoggerText.DB_ERROR.getText(), sessionId, RestProcedure.delete_parent, e.getMessage());
            status = Utils.parseError(e);
            logger.error(LoggerText.REST_ERROR.getText(), sessionId, RestProcedure.delete_parent, status);
        } catch (ValidateException e) {
            logger.error(LoggerText.VALIDATE_ERROR.getText(), sessionId, RestProcedure.delete_parent, e.getMessage());
            status = HttpStatus.BAD_REQUEST;
            logger.error(LoggerText.REST_ERROR.getText(), sessionId, RestProcedure.delete_parent, status);
        }
        return new ResponseEntity(status);
    }

    @DeleteMapping("/child/{msisdn:\\w+}")
    public ResponseEntity delChild(@PathVariable String msisdn) {
        long startExec = System.currentTimeMillis();
        final String sessionId = Utils.createUuid();
        HttpStatus status = HttpStatus.NO_CONTENT;
        if (logger.isDebugEnabled()) {
            logger.debug(LoggerText.REST_REQUEST.getText(), RestProcedure.delete_child, sessionId, "msisdn=" + msisdn);
        }
        try {
            Utils.validateMsisdn(msisdn);
            dbService.delChild(sessionId, msisdn);
            long endExec = System.currentTimeMillis();
            if (logger.isDebugEnabled()) {
                logger.debug(LoggerText.SQL_RESPONSE.getText(), sessionId, RestProcedure.delete_child, endExec - startExec);
            }
        } catch (DbException e) {
            logger.error(LoggerText.DB_ERROR.getText(), sessionId, RestProcedure.delete_child, e.getMessage());
            status = Utils.parseError(e);
            logger.error(LoggerText.REST_ERROR.getText(), sessionId, RestProcedure.delete_child, status);
        } catch (ValidateException e) {
            logger.error(LoggerText.VALIDATE_ERROR.getText(), sessionId, RestProcedure.delete_child, e.getMessage());
            status = HttpStatus.BAD_REQUEST;
            logger.error(LoggerText.REST_ERROR.getText(), sessionId, RestProcedure.delete_child, status);
        }
        return new ResponseEntity(status);
    }

    @PatchMapping("/child/{msisdn:\\w+}")
    public ResponseEntity setChild(@PathVariable String msisdn, @RequestBody Flags flags) {
        long startExec = System.currentTimeMillis();
        final String sessionId = Utils.createUuid();
        HttpStatus status = HttpStatus.OK;
        if (logger.isDebugEnabled()) {
            logger.debug(LoggerText.REST_REQUEST.getText(), RestProcedure.set_child, sessionId, "msisdn=" + msisdn + "\n" + flags);
        }
        try {
            Utils.validateMsisdn(msisdn);
            Utils.validateFlags(flags.getFwdAoc(), flags.getFwdPay());
            dbService.setChild(sessionId, msisdn, flags);
            long endExec = System.currentTimeMillis();
            if (logger.isDebugEnabled()) {
                logger.debug(LoggerText.SQL_RESPONSE.getText(), sessionId, RestProcedure.set_child, endExec - startExec);
            }
        } catch (DbException e) {
            logger.error(LoggerText.DB_ERROR.getText(), sessionId, RestProcedure.set_child, e.getMessage());
            status = Utils.parseError(e);
            logger.error(LoggerText.REST_ERROR.getText(), sessionId, RestProcedure.set_child, status);
        } catch (ValidateException e) {
            logger.error(LoggerText.VALIDATE_ERROR.getText(), sessionId, RestProcedure.set_child, e.getMessage());
            status = HttpStatus.BAD_REQUEST;
            logger.error(LoggerText.REST_ERROR.getText(), sessionId, RestProcedure.set_child, status);
        }
        return new ResponseEntity(status);
    }

    @GetMapping("/msisdn/{msisdn:\\w+}")
    public ResponseEntity getMsisdn(@PathVariable String msisdn) {
        long startExec = System.currentTimeMillis();
        final String sessionId = Utils.createUuid();
        HttpStatus status = HttpStatus.OK;
        GetMsisdnResponse response = null;
        if (logger.isDebugEnabled()) {
            logger.debug(LoggerText.REST_REQUEST.getText(), RestProcedure.get_msisdn, sessionId, "msisdn=" + msisdn);
        }
        try {
            Utils.validateMsisdn(msisdn);
            response = dbService.getAbonentType(sessionId, msisdn);
            long endExec = System.currentTimeMillis();
            if (logger.isDebugEnabled()) {
                logger.debug(LoggerText.SQL_RESPONSE.getText(), sessionId, RestProcedure.get_msisdn, endExec - startExec);
            }
        } catch (ValidateException e) {
            logger.error(LoggerText.VALIDATE_ERROR.getText(), sessionId, RestProcedure.get_msisdn, e.getMessage());
            status = HttpStatus.BAD_REQUEST;
            logger.error(LoggerText.REST_ERROR.getText(), sessionId, RestProcedure.get_msisdn, status);
        } catch (DbException e) {
            logger.error(LoggerText.DB_ERROR.getText(), sessionId, RestProcedure.get_msisdn, e.getMessage());
            status = Utils.parseError(e);
            logger.error(LoggerText.REST_ERROR.getText(), sessionId, RestProcedure.get_msisdn, status);
        }
        return ResponseEntity.status(status).body(response);
    }

}
