package de.jbee.earthworm.module;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import de.jbee.earthworm.data.IData.IDataPath;
import de.jbee.earthworm.data.IData.IListPath;
import de.jbee.earthworm.data.IData.IValuePath;

public class Template<T>
		implements IComponent<T> {

	private final List<TemplateAttr<T, ?>> attrs = new LinkedList<TemplateAttr<T, ?>>();
	private final List<TemplateTag<T>> tags = new LinkedList<TemplateTag<T>>();

	// name als Teil von Tag oder hier in die methode aufnehmen ?

	// eigentlich will man hier noch typen damit attribute nur formal korrekte werte bekommen können
	// das wären String / enum / int - man muss von allen auf string kommen

	public static class DataBinder<T>
			extends ComponentBinder<T> {

		private final Template<T> template;
		private final ITag tag;

		DataBinder( Template<T> template, ITag tag ) {
			super();
			this.template = template;
			this.tag = tag;
		}

		// man erkennt ja am markup, ob es nur ein leers platzhalter-tag ist (ersetzen) oder es inhalt gibt (davon ausgehen, dass die component das berücksichtigt
		public <V> ComponentBinder<V> using( IDataPath<? super T, V> path ) {

			return null;
		}

		public <V> ListComponentBinder<V> listing( IListPath<? super T, V> path ) {
			return null;
		}

		// TODO das kann man sogar noch als builder weiterführen - sodass man beliebige folge "uses" bedingt zeigen kann 
		public ComponentBinder<T> dependsOn( IValuePath<? super T, Boolean> path ) {
			return dependsOn( Fulfills.trueValue( path ) );
		}

		public ComponentBinder<T> dependsOn( IConditional<? super T> condition ) {

			return null;
		}

		private void bindTag( ITag tag, IComponent<? super T> component ) {
			template.tags.add( new TemplateTag<T>( tag, component ) );
		}
	}

	public static class ComponentBinder<T> {

		public void is( IComponent<? super T> component ) {

		}

		public void is( Class<? extends IComponent<? super T>> type ) {

		}
	}

	public static class ListComponentBinder<T>
			extends ComponentBinder<T> {

		public void is( IComponent<? super T>... components ) {

		}

		public void is( Class<? extends IComponent<? super T>>... components ) {

		}
	}

	public DataBinder<T> the( ITag tag ) {
		return null;
	}

	public <V extends CharSequence> void bind( IAttr<V> attr, IValuePath<? super T, V> path ) {

	}

	@Override
	public void prepare( IPreparationCycle<? extends T> cycle ) {
		// load markup
		String markup = "a fix example";
		// find and sort used/bound tags and split markup appropriate -> create simple statically elements
		SortedMap<Integer, IComponent<T>> positions = new TreeMap<Integer, IComponent<T>>();
		for ( TemplateAttr<T, ?> b : attrs ) {
			int pos = markup.indexOf( b.attr.name() );
			positions.put( pos, b );
		}
		// insert:
		// - and unify components
		// - values
		// - renderable content
		int lastPos = 0;
		for ( Entry<Integer, IComponent<T>> e : positions.entrySet() ) {
			String before = markup.substring( lastPos, e.getKey() );
			cycle.constant( before );
			IComponent<T> comp = e.getValue();
			if ( comp instanceof ITemplateComponent<?> ) {
				String tagMarkup = "compute it here";
				( (ITemplateComponent<T>) comp ).prepareSubstitutional( tagMarkup, cycle );
			} else {
				comp.prepare( cycle );
			}
		}
	}

	// IBindable interface um sicherzustellen, dass Components auch für templates gedacht sind

	static class TemplateAttr<T, V extends CharSequence>
			implements IComponent<T> {

		final IAttr<V> attr;
		final IValuePath<T, V> path;

		TemplateAttr( IAttr<V> tag, IValuePath<T, V> path ) {
			super();
			this.attr = tag;
			this.path = path;
		}

		@Override
		public void prepare( IPreparationCycle<? extends T> cycle ) {
			cycle.variable( path );
		}

	}

	static class TemplateTag<T>
			implements IComponent<T> {

		final ITag tag;
		final IComponent<? super T> component;

		TemplateTag( ITag tag, IComponent<? super T> component ) {
			super();
			this.tag = tag;
			this.component = component;
		}

		@Override
		public void prepare( IPreparationCycle<? extends T> cycle ) {
			component.prepare( cycle );
		}
	}
}
