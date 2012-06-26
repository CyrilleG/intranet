package filters;
/**
 * needed by all filters (extends) 
 * @author Cyrille
 *
 */
public abstract class Filter {
	/**
	 * do nothing, filters just need a public constructor (without parameters) for their instances.
	 */
	public Filter()
	{
		
	}
	/**
	 * need to present for all filters (even if empty)
	 */
	public abstract void preFilter();
	/**
	 * need to present for all filters (even if empty)
	 */
	public abstract void postFilter();
}
