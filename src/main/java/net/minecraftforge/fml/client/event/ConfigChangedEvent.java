/*
 * Forge Mod Loader
 * Copyright (c) 2012-2014 cpw.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * Contributors (this class):
 *     bspkrs - implementation
 */

package net.minecraftforge.fml.client.event;

import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.Event.HasResult;

@HasResult
public class ConfigChangedEvent extends Event
{
    public final String  modID;
    public final boolean isWorldRunning;
    public final boolean requiresMcRestart;
    public final String configID;
    
    public ConfigChangedEvent(String modID, String configID, boolean isWorldRunning, boolean requiresMcRestart)
    {
        this.modID = modID;
        this.configID = configID;
        this.isWorldRunning = isWorldRunning;
        this.requiresMcRestart = requiresMcRestart;
    }
    
    public static class OnConfigChangedEvent extends ConfigChangedEvent
    {
        public OnConfigChangedEvent(String modID, String configID, boolean isWorldRunning, boolean requiresMcRestart)
        {
            super(modID, configID, isWorldRunning, requiresMcRestart);
        }
    }
    
    public static class PostConfigChangedEvent extends ConfigChangedEvent
    {
        public PostConfigChangedEvent(String modID, String configID, boolean isWorldRunning, boolean requiresMcRestart)
        {
            super(modID, configID, isWorldRunning, requiresMcRestart);
        }
    }
}
