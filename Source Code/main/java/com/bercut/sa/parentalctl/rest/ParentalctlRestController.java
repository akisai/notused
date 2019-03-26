package com.bercut.sa.parentalctl.rest;

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
        final String sessionId = Utils.createUuid();
        HttpStatus status = HttpStatus.CREATED;
        if (logger.isDebugEnabled()) {
            logger.debug(LoggerText.REST_REQUEST.getText(), RestProcedure.add_parent, sessionId, msisdn.toString());
        }
        try {
            Utils.validateMsisdn(msisdn.getMsisdn());
            dbService.addParent(sessionId, msisdn);
        } catch (SQLException e) {
            logger.error(LoggerText.SQL_ERROR.getText(), sessionId, RestProcedure.add_parent, e.getMessage());
            status = parseError(e);
            logger.error(LoggerText.REST_ERROR.getText(), sessionId, RestProcedure.add_parent, status);
        }
        return new ResponseEntity(status);
    }

    @PostMapping("/child")
    public ResponseEntity addChildren(@RequestBody Children children) {
        final String sessionId = Utils.createUuid();
        HttpStatus status = HttpStatus.CREATED;
        if (logger.isDebugEnabled()) {
            logger.debug(LoggerText.REST_REQUEST.getText(), RestProcedure.add_children, sessionId, children.toString());
        }
        try {
            Utils.validateMsisdn(children.getMsisdn(), children.getParent());
            Utils.validateFlags(children.getFwdAoc(), children.getFwdPay());
            dbService.addChild(sessionId, children);
        } catch (SQLException e) {
            logger.error(LoggerText.SQL_ERROR.getText(), sessionId, RestProcedure.add_children, e.getMessage());
            status = parseError(e);
            logger.error(LoggerText.REST_ERROR.getText(), sessionId, RestProcedure.add_children, status);
        }
        return new ResponseEntity(status);
    }

    @DeleteMapping("/parent/{msisdn:\\w+}")
    public ResponseEntity delParent(@PathVariable String msisdn) {
        final String sessionId = Utils.createUuid();
        HttpStatus status = HttpStatus.NO_CONTENT;
        if (logger.isDebugEnabled()) {
            logger.debug(LoggerText.REST_REQUEST.getText(), RestProcedure.delete_parent, sessionId, "msisdn= " + msisdn);
        }
        try {
            Utils.validateMsisdn(msisdn);
            dbService.delParent(sessionId, msisdn);
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
            logger.debug(LoggerText.REST_REQUEST.getText(), RestProcedure.delete_children, sessionId, "msisdn= " + msisdn);
        }
        try {
            Utils.validateMsisdn(msisdn);
            dbService.delChild(sessionId, msisdn);
        } catch (SQLException e) {
            logger.error(LoggerText.SQL_ERROR.getText(), sessionId, RestProcedure.delete_children, e.getMessage());
            status = parseError(e);
            logger.error(LoggerText.REST_ERROR.getText(), sessionId, RestProcedure.delete_children, status);
        }
        return new ResponseEntity(status);
    }

    @PatchMapping("/child/{msisdn:\\w+}")
    public ResponseEntity setChild(@PathVariable String msisdn, @RequestBody Flags flags) {
        final String sessionId = Utils.createUuid();
        HttpStatus status = HttpStatus.OK;
        if (logger.isDebugEnabled()) {
            logger.debug(LoggerText.REST_REQUEST.getText(), RestProcedure.set_children, sessionId, "msisdn= " + msisdn);
        }
        try {
            Utils.validateMsisdn(msisdn);
            Utils.validateFlags(flags.getFwdAoc(), flags.getFwdPay());
            dbService.setChild(sessionId, msisdn, flags);
        } catch (SQLException e) {
            logger.error(LoggerText.SQL_ERROR.getText(), sessionId, RestProcedure.set_children, e.getMessage());
            status = parseError(e);
            logger.error(LoggerText.REST_ERROR.getText(), sessionId, RestProcedure.set_children, status);
        }
        return new ResponseEntity(status);
    }

    private HttpStatus parseError(SQLException e) {
        switch (e.getErrorCode()) {
            case 20100:
                return HttpStatus.NOT_ACCEPTABLE; //406
            case 20101:
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
