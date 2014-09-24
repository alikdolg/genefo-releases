package com.genefo.services;

import java.io.Serializable;

import com.genefo.persistence.models.Role;

/**
 * 
 * @author Alexey
 *
 */
public interface RoleService<ID extends Serializable> extends ReadOnlyServices<Role, ID> {

}
