package org.jrebirth.core.command.basic;

import org.jrebirth.core.command.AbstractBaseMultiCommand;
import org.jrebirth.core.concurrent.RunIntoType;
import org.jrebirth.core.wave.WaveBean;

/**
 * The class <strong>ShowModelCommand</strong>.
 * 
 * @author Sébastien Bordes
 */
public class ShowModelCommand extends AbstractBaseMultiCommand {

    /**
     * Default Constructor.
     */
    public ShowModelCommand() {
        super(RunIntoType.JIT);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void addSubCommand() {
        addCommandClass(PrepareModelCommand.class);
        addCommandClass(AttachModelCommand.class);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<? extends WaveBean> getWaveBeanClass() {
        return ShowModelWaveBean.class;
    }

}
