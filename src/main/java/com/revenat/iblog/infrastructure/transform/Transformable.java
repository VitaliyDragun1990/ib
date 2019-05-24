package com.revenat.iblog.infrastructure.transform;

/**
 * Any object that supports direct/backward transformation into some kind of
 * objects
 * 
 * @author Vitaly Dragun
 *
 */
public interface Transformable<P> {

	/**
	 * Transforms given object into current one
	 * 
	 * @param p object to transform from
	 */
	void transform(P p);

	/**
	 * Transforms current object into given one
	 * 
	 * @param p object to transform to
	 * @return transformed object
	 */
	P untransform(P p);
}
