package org.lamport.tla.toolbox.tool.tlc.job;

import org.eclipse.debug.core.ILaunch;
import org.eclipse.swt.widgets.Display;

/**
 * Extends {@link TLCProcessJob}.
 * 
 * Currently, the only differences between this class and
 * {@link TLCProcessJob} are the way in which
 * the results of the trace explorer are presented. The results
 * of trace exploration are always displayed upon completion of
 * TLC. In a run of TLC for model checking, the results may not be
 * displayed if the user chooses to run the job in the background.
 * 
 * This job also has a shorter timeout than {@link TLCProcessJob} when
 * checking if TLC is still running. This seems to make the trace explorer
 * run faster.
 * 
 * @author Daniel Ricketts
 *
 */
public class TraceExplorerJob extends TLCProcessJob
{

    public TraceExplorerJob(String specName, String modelName, ILaunch launch)
    {
        super(specName, modelName, launch);
    }

    /**
     * Always asynchronously runs the action returned by
     * {@link AbstractJob#getJobCompletedAction()}.
     */
    public void doFinish()
    {
        Display.getDefault().asyncExec(new Runnable() {
            public void run()
            {
                getJobCompletedAction().run();
            }
        });
    }

    /**
     * Checks if TLC is still running with a shorter timeout
     * than is used in {@link TLCProcessJob}.
     * 
     * Trace exploration is much faster, so the timeout can
     * be shorter. This seems to make the trace explorer
     * run faster.
     * 
     * @see {@link TLCJob#checkAndSleep()}
     */
    public boolean checkAndSleep()
    {
        try
        {
            // go sleep
            Thread.sleep(100);

        } catch (InterruptedException e)
        {
            // nothing to do
            // e.printStackTrace();
        }
        // return true if the TLC is still calculating
        return (!process.isTerminated());
    }

}