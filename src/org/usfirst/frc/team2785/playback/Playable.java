package org.usfirst.frc.team2785.playback;

public interface Playable {
    public void playerSetup(Player p);
    public void play();
    public boolean donePlaying();
    public void stopPlaying();
    public void setPlayerUsePID(boolean yes);

}
