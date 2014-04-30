/**	 Cycle Altanta, Copyright 2012 Georgia Institute of Technology
 *                                    Atlanta, GA. USA
 *
 *   @author Christopher Le Dantec <ledantec@gatech.edu>
 *   @author Anhong Guo <guoanhong15@gmail.com>
 *
 *   Updated/Modified for Atlanta's app deployment. Based on the
 *   CycleTracks codebase for SFCTA.
 *
 *   CycleTracks, Copyright 2009,2010 San Francisco County Transportation Authority
 *                                    San Francisco, CA, USA
 *
 * 	 @author Billy Charlton <billy.charlton@sfcta.org>
 *
 *   This file is part of CycleTracks.
 *
 *   CycleTracks is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   CycleTracks is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with CycleTracks.  If not, see <http://www.gnu.org/licenses/>.
 */

package edu.gatech.ppl.cycleatlanta;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.provider.Settings;

public class Feedback extends Activity
{
	final  Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
	final int go = 700;
	final int pause = 200;
	final long[] pattern = {0, go, pause, go, pause, go};
	final MediaPlayer mp = MediaPlayer.create(this, Settings.System.DEFAULT_NOTIFICATION_URI);

	public void giveFeedback()
	{
		vibe.vibrate(pattern, -1);
		mp.start();
	}

}

