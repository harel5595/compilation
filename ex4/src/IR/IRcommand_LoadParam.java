package IR;

import TEMP.TEMP;
import Useable.UseableClass;

public class IRcommand_LoadParam extends IRcommand {
    TEMP dst;
    int paran_number_from_end;

	public IRcommand_LoadParam(TEMP dst, int param_number_from_end)
    {
            this.dst = dst;
            this.paran_number_from_end = param_number_from_end;
    }

        /***************/
        /* MIPS me !!! */
        /***************/
        public void MIPSme()
        {
            //TODO: this.

            //MIPSGenerator.getInstance().load(dst,var_name);
        }
}
