/*
 * Copyright 2009 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */

/*
 * @test
 * @bug 6669869
 * @summary Tests DesignTime property in different application contexts
 * @author Sergey Malenkov
 */

import java.beans.Beans;
import sun.awt.SunToolkit;

public class TestDesignTime implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        if (Beans.isDesignTime()) {
            throw new Error("unexpected DesignTime property");
        }
        Beans.setDesignTime(!Beans.isDesignTime());
        ThreadGroup group = new ThreadGroup("$$$");
        Thread thread = new Thread(group, new TestDesignTime());
        thread.start();
        thread.join();
    }

    public void run() {
        SunToolkit.createNewAppContext();
        if (Beans.isDesignTime()) {
            throw new Error("shared DesignTime property");
        }
    }
}
