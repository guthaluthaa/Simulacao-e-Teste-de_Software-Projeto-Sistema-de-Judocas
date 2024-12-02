package org.fpij.jitakyoei;

import org.fpij.jitakyoei.facade.AppFacade;
import org.fpij.jitakyoei.view.AppView;

// Classe mock para AppView
public class MockAppView implements AppView {
    @Override
    public void handleModelChange(Object obj) {
        // Implementação vazia para testes
    }
    @Override
    public void displayException(Exception e){

    }
    @Override
    public void registerFacade(AppFacade facade){

    }

    // Implemente outros métodos se necessário
}