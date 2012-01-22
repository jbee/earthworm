package de.jbee.earthworm;

import de.jbee.earthworm.data.IData.IDataPath;
import de.jbee.earthworm.data.IData.IListPath;
import de.jbee.earthworm.data.IData.IValuePath;
import de.jbee.earthworm.module.Attr;
import de.jbee.earthworm.module.Component;
import de.jbee.earthworm.module.Container;
import de.jbee.earthworm.module.IComponent;
import de.jbee.earthworm.module.IPreparationCycle;
import de.jbee.earthworm.module.ITag;
import de.jbee.earthworm.module.Label;
import de.jbee.earthworm.module.Template;

public class CodeProof {

	static interface Album {

		IValuePath<Album, String> title = null;
		IValuePath<Album, Boolean> ep = null;
		IListPath<Album, Track> tracks = null;
		IDataPath<Album, Track> hiddenTrack = null;
	}

	static interface CdBox
			extends Album {

		IValuePath<CdBox, String> subtitle = null;
	}

	static interface Track {

		IValuePath<Track, Integer> number = null;
		IValuePath<Track, String> title = null;
		IValuePath<Track, Long> length = null;
		IListPath<Track, Artist> artists = null;
	}

	static interface Artist {

		IValuePath<Artist, String> name = null;
	}

	static enum MyTemplateTags
			implements ITag {
		hiddenTrack,
		foo,
		tracks,
		ep
	}

	static class MyPage
			implements IComponent<CdBox> {

		@Override
		public void prepare( IPreparationCycle<? extends CdBox> cycle ) {
			Template<Album> t = new Template<Album>();
			t.bind( Attr.name, Album.title );
			t.the( MyTemplateTags.hiddenTrack ).using( Album.hiddenTrack ).is( new TrackRow() );
			t.the( MyTemplateTags.foo ).is( Component.markup( "get out" ) );
			t.the( MyTemplateTags.ep ).dependsOn( Album.ep ).is(
					Component.markup( "This is a EP Album" ) );
			t.the( MyTemplateTags.tracks ).listing( Album.tracks ).is( TrackRow.class,
					TrackRow.class );
			t.prepare( cycle );
			Label<Album> test = new Label<Album>( Album.title );
			test.prepare( cycle );
			Label<CdBox> test2 = new Label<CdBox>( CdBox.subtitle );
			test2.prepare( cycle );
		}
	}

	static class TrackRow
			implements IComponent<Track> {

		@Override
		public void prepare( IPreparationCycle<? extends Track> cycle ) {
			cycle.variable( Track.title );
			IPreparationCycle<Artist> artists = cycle.in( Container.repeats( Track.artists ) );
			artists.in( Container.<Artist> recoversByStrippingOut() ).variable( Artist.name );
		}
	}

}
