package com.qa.ims.controller;

import java.util.List;

/**
 * Create, Read, Update and Delete controller. Takes in inputs for each action
 * to be sent to a service class
 */
public interface CrudController<T> { // TODO add a read one to this?  Can't see any reason why not...

	List<T> readAll();

	T create();

	T update();

	int delete();

}
