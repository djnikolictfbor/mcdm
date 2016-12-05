package org.mcdm;
import org.xmcda.*;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashSet;

public class PrometheeII {

    protected XMCDA xmcda;
    protected AlternativesCriteriaValues currentCriteria;

    public PrometheeII(XMCDA core)
    {
        xmcda = core;
    }

    public Alternative FindClosest(List<Alternative> centralProfiles, Alternative tested)
    {
        CalculatePI(centralProfiles.get(0), tested);
        return null;
    }

    public void SetCriteria(AlternativesCriteriaValues criteria)
    {
        currentCriteria = criteria;
    }

    public double getIndifference(Criterion crit)
    {
        CriteriaThresholds ctr = xmcda.criteriaThresholdsList.get(0);

        CriterionThresholds criterionT = ctr.get(crit);

        if (criterionT.get(0).id().equals("indifference"))
            return (double)criterionT.get(0).getConstant().getValue();
        else
            return (double)criterionT.get(1).getConstant().getValue();
    }

    public double getPreference(Criterion crit)
    {
        CriteriaThresholds ctr = xmcda.criteriaThresholdsList.get(0);

        CriterionThresholds criterionT = ctr.get(crit);

        if (criterionT.get(0).id().equals("preference"))
            return (double)criterionT.get(0).getConstant().getValue();
        else
            return (double)criterionT.get(1).getConstant().getValue();
    }

    double functionPMax(double indf, double pref, double x)
    {
        if (x < pref)
            return 1.0;
        else if (x < indf)
            return (indf - x)/(indf - pref);
        else
            return 0.0;
    }

    double functionPMin(double indf, double pref, double x)
    {
        if (x < indf)
            return 0.0;
        else if (x < pref)
            return (x - indf)/(pref - indf);
        else
            return 1.0;
    }


    private double CalculatePI(Alternative first, Alternative second)
    {
        CriteriaValues firstAlter = (CriteriaValues)currentCriteria.get(first);
        CriteriaValues secondAlter = (CriteriaValues)currentCriteria.get(second);
        for (Criterion crit : xmcda.criteria)
        {
            Double result = 0.0;

            LabelledQValues secLQV = (LabelledQValues)secondAlter.get(crit);
            QualifiedValue secQV = (QualifiedValue)secLQV.get(0);
            Double sec = (double)secQV.getValue();


            LabelledQValues fiLQV = (LabelledQValues)firstAlter.get(crit);
            QualifiedValue fiQV = (QualifiedValue)fiLQV.get(0);
            Double fir = (double)fiQV.getValue();

            Double dk = fir - sec;

            Double indif  = getIndifference(crit);
            Double pref  = getPreference(crit);

            QuantitativeScale x = (QuantitativeScale)xmcda.criteriaScalesList.get(0).get(crit).get(0);
            if (x.getPreferenceDirection() == Scale.PreferenceDirection.MIN)
                result = functionPMax(indif, pref, dk);
            else
                result = functionPMin(indif, pref, dk);



            crit.equals(null);
        }


//        //int firstIndex = currentCriteria.get(first);
//        int secondIndex = currentCriteria.getAlternativeCriteriaValue().lastIndexOf(second);
//
//        for (Criterion crt : xmcda.criteria)
//        {
//            double dk = (double)currentCriteria.getAlternativeCriteriaValue().get(firstIndex).getCriterionValue().get(0).getValueOrValues().get(0);
//
//
//
//        }
//

        return 0;
    }
}