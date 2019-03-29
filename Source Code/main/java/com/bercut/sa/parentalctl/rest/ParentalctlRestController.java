package com.bercut.sa.parentalctl.rest;

import com.bercut.sa.parentalctl.atlas.AtlasProvider;
import com.bercut.sa.parentalctl.db.DbService;
import com.bercut.sa.parentalctl.logs.LoggerText;
import com.bercut.sa.parentalctl.rest.model.Children;
import com.bercut.sa.parentalctl.rest.model.Flags;
import com.bercut.sa.parentalctl.rest.model.Msisdn;
import com.bercut.sa.parentalctl.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.sql.SQLException;
import java.util.Date;

@RestController
@RequestMapping("/v1")
@EnableWebMvc
public class ParentalctlRestController {

    private DbService dbService;
    private AtlasProvider atlas;

    @Autowired
    public ParentalctlRestController(DbService dbService, AtlasProvider atlas) {
        this.dbService = dbService;
        this.atlas = atlas;
    }

    private final Logger logger = LoggerFactory.getLogger(ParentalctlRestController.class);

    @PostMapping("/parent")
    public ResponseEntity addParent(@RequestBody Msisdn msisdn) {
        final String sessionId = Utils.createUuid();
        HttpStatus status = HttpStatus.CREATED;
        if (logger.isDebugEnabled()) {
            logger.debug(LoggerText.REST_REQUEST.getText(), RestProcedure.add_parent, sessionId, msisdn);
        }
        try {
            Utils.validateMsisdn(msisdn.getMsisdn());
            long startExec = new Date().getTime();
            dbService.addParent(sessionId, msisdn);
            long endExec = new Date().getTime();
            if (logger.isDebugEnabled()) {
                logger.debug(LoggerText.SQL_RESPONSE.getText(), sessionId, RestProcedure.add_parent, endExec - startExec);
            }
        } catch (SQLException e) {
            logger.error(LoggerText.SQL_ERROR.getText(), sessionId, RestProcedure.add_parent, e.getMessage());
            status = parseError(e);
            logger.error(LoggerText.REST_ERROR.getText(), sessionId, RestProcedure.add_parent, status);
        }
        return new ResponseEntity(status);
    }

    @PostMapping("/child")
    public ResponseEntity addChild(@RequestBody Children children) {
        final String sessionId = Utils.createUuid();
        HttpStatus status = HttpStatus.CREATED;
        if (logger.isDebugEnabled()) {
            logger.debug(LoggerText.REST_REQUEST.getText(), RestProcedure.add_child, sessionId, children.toString());
        }
        try {
            Utils.validateMsisdn(children.getMsisdn(), children.getParent());
            if(children.getMsisdn().equals(children.getParent())) {
                throw new SQLException("Equals msisdn", null, 20102);
            }
            Utils.validateFlags(children.getFwdAoc(), children.getFwdPay());
            long startExec = new Date().getTime();
            dbService.addChild(sessionId, children);
            long endExec = new Date().getTime();
            if (logger.isDebugEnabled()) {
                logger.debug(LoggerText.SQL_RESPONSE.getText(), sessionId, RestProcedure.add_child, endExec - startExec);
            }
        } catch (SQLException e) {
            logger.error(LoggerText.SQL_ERROR.getText(), sessionId, RestProcedure.add_child, e.getMessage());
            status = parseError(e);
            logger.error(LoggerText.REST_ERROR.getText(), sessionId, RestProcedure.add_child, status);
        }
        return new ResponseEntity(status);
    }

    @DeleteMapping("/parent/{msisdn:\\w+}")
    public ResponseEntity delParent(@PathVariable String msisdn) {
        final String sessionId = Utils.createUuid();
        HttpStatus status = HttpStatus.NO_CONTENT;
        if (logger.isDebugEnabled()) {
            logger.debug(LoggerText.REST_REQUEST.getText(), RestProcedure.delete_parent, sessionId, "msisdn=" + msisdn);
        }
        try {
            Utils.validateMsisdn(msisdn);
            long startExec = new Date().getTime();
            dbService.delParent(sessionId, msisdn);
            long endExec = new Date().getTime();
            if (logger.isDebugEnabled()) {
                logger.debug(LoggerText.SQL_RESPONSE.getText(), sessionId, RestProcedure.delete_parent, endExec - startExec);
            }
        } catch (SQLException e) {
            logger.error(LoggerText.SQL_ERROR.getText(), sessionId, RestProcedure.delete_parent, e.getMessage());
            status = parseError(e);
            logger.error(LoggerText.REST_ERROR.getText(), sessionId, RestProcedure.delete_parent, status);
        }
        return new ResponseEntity(status);
    }

    @DeleteMapping("/child/{msisdn:\\w+}")
    public ResponseEntity delChild(@PathVariable String msisdn) {
        final String sessionId = Utils.createUuid();
        HttpStatus status = HttpStatus.NO_CONTENT;
        if (logger.isDebugEnabled()) {
            logger.debug(LoggerText.REST_REQUEST.getText(), RestProcedure.delete_child, sessionId, "msisdn=" + msisdn);
        }
        try {
            Utils.validateMsisdn(msisdn);
            long startExec = new Date().getTime();
            dbService.delChild(sessionId, msisdn);
            long endExec = new Date().getTime();
            if (logger.isDebugEnabled()) {
                logger.debug(LoggerText.SQL_RESPONSE.getText(), sessionId, RestProcedure.delete_child, endExec - startExec);
            }
        } catch (SQLException e) {
            logger.error(LoggerText.SQL_ERROR.getText(), sessionId, RestProcedure.delete_child, e.getMessage());
            status = parseError(e);
            logger.error(LoggerText.REST_ERROR.getText(), sessionId, RestProcedure.delete_child, status);
        }
        return new ResponseEntity(status);
    }

    @PatchMapping("/child/{msisdn:\\w+}")
    public ResponseEntity setChild(@PathVariable String msisdn, @RequestBody Flags flags) {
        final String sessionId = Utils.createUuid();
        HttpStatus status = HttpStatus.OK;
        if (logger.isDebugEnabled()) {
            logger.debug(LoggerText.REST_REQUEST.getText(), RestProcedure.set_child, sessionId, "msisdn=" + msisdn + "\n" + flags);
        }
        try {
            Utils.validateMsisdn(msisdn);
            Utils.validateFlags(flags.getFwdAoc(), flags.getFwdPay());
            long startExec = new Date().getTime();
            dbService.setChild(sessionId, msisdn, flags);
            long endExec = new Date().getTime();
            if (logger.isDebugEnabled()) {
                logger.debug(LoggerText.SQL_RESPONSE.getText(), sessionId, RestProcedure.set_child, endExec - startExec);
            }
        } catch (SQLException e) {
            logger.error(LoggerText.SQL_ERROR.getText(), sessionId, RestProcedure.set_child, e.getMessage());
            status = parseError(e);
            logger.error(LoggerText.REST_ERROR.getText(), sessionId, RestProcedure.set_child, status);
        }
        return new ResponseEntity(status);
    }

    private HttpStatus parseError(SQLException e) {
        switch (e.getErrorCode()) {
            case 20100:
                return HttpStatus.NOT_ACCEPTABLE; //406
            case 1://20101:
                return HttpStatus.CONFLICT; //409
            case 20102:
                return HttpStatus.BAD_REQUEST; //400
            case 20103:
                return HttpStatus.NOT_FOUND; //404
            default:
                return HttpStatus.INTERNAL_SERVER_ERROR; //500
        }
    }

}
