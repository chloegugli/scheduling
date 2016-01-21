/*
 * ProActive Parallel Suite(TM): The Java(TM) library for
 *    Parallel, Distributed, Multi-Core Computing for
 *    Enterprise Grids & Clouds
 *
 * Copyright (C) 1997-2015 INRIA/University of
 *                 Nice-Sophia Antipolis/ActiveEon
 * Contact: proactive@ow2.org or contact@activeeon.com
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; version 3 of
 * the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
 *
 * Initial developer(s):               The ProActive Team
 *                         http://proactive.inria.fr/team_members.htm
 */
package org.ow2.proactive_grid_cloud_portal.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility container to pass multiple parameters for tasks sorting.
 *
 * @author ActiveEon Team
 */
public class SortSpecifierRestContainer implements Serializable {

    public List<SortSpecifierRestItem> sortParameters = null;

    public SortSpecifierRestContainer() {
        sortParameters = new ArrayList<>();
    }

    public class SortSpecifierRestItem implements Serializable {

        protected String field;
        protected String order;

        SortSpecifierRestItem(String field, String order) {
            this.field = field;
            this.order = order;
        }

        public SortSpecifierRestItem() {
            this.field = "NOTSET";
            this.order = "ASCENDING";
        }

        public String toString() {
            return field + "," + order;
        }

        public String getField() {
            return field;
        }

        public String getOrder() {
            return order;
        }
    }

    public SortSpecifierRestContainer(int size) {
        sortParameters = new ArrayList<>(size);
    }

    public SortSpecifierRestContainer(String values) {
        sortParameters = new ArrayList<>();
        for (String s : values.split(";")) {
            String[] sortParam = s.split(",");
            add(sortParam[0], sortParam[1]);
        }
    }

    public void add(String field, String order) {
        sortParameters.add(new SortSpecifierRestItem(field, order));
    }

    public List<SortSpecifierRestItem> getSortParameters() {
        return sortParameters;
    }

}
