package myApp.michal.crossNote;

import android.util.SparseArray;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.util.HumanReadables;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.ArrayList;
import java.util.List;

import static androidx.test.espresso.intent.Checks.checkArgument;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.hamcrest.core.AllOf.allOf;

public final class CustomRecyclerViewActions {
        private static final int NO_POSITION = -1;
        private CustomRecyclerViewActions() {
            // no instance
        }

        public interface PositionableRecyclerViewAction extends ViewAction {
            public PositionableRecyclerViewAction atPosition(int position);
        }

        public static <VH extends RecyclerView.ViewHolder> PositionableRecyclerViewAction scrollToHolder(
                final Matcher<VH> viewHolderMatcher) {
            return new ScrollToViewAction<VH>(viewHolderMatcher);
        }

        public static <VH extends RecyclerView.ViewHolder> PositionableRecyclerViewAction scrollTo(
                final Matcher<View> itemViewMatcher) {
            Matcher<VH> viewHolderMatcher = viewHolderMatcher(itemViewMatcher);
            return new ScrollToViewAction<VH>(viewHolderMatcher);
        }

        public static <VH extends RecyclerView.ViewHolder> ViewAction scrollToPosition(final int position) {
            return new ScrollToPositionViewAction(position);
        }

        public static <VH extends RecyclerView.ViewHolder> PositionableRecyclerViewAction actionOnItem(
                final Matcher<View> itemViewMatcher, final ViewAction viewAction) {
            Matcher<VH> viewHolderMatcher = viewHolderMatcher(itemViewMatcher);
            return new ActionOnItemViewAction<VH>(viewHolderMatcher, viewAction);
        }

        public static <VH extends RecyclerView.ViewHolder> PositionableRecyclerViewAction actionOnHolderItem(
                final Matcher<VH> viewHolderMatcher, final ViewAction viewAction) {
            return new ActionOnItemViewAction<VH>(viewHolderMatcher, viewAction);
        }
        private static final class ActionOnItemViewAction<VH extends RecyclerView.ViewHolder> implements
                PositionableRecyclerViewAction {
            private final Matcher<VH> viewHolderMatcher;
            private final ViewAction viewAction;
            private final int atPosition;
            private final ScrollToViewAction<VH> scroller;
            private ActionOnItemViewAction(Matcher<VH> viewHolderMatcher, ViewAction viewAction) {
                this(viewHolderMatcher, viewAction, NO_POSITION);
            }
            private ActionOnItemViewAction(Matcher<VH> viewHolderMatcher, ViewAction viewAction,
                                           int atPosition) {
                this.viewHolderMatcher = checkNotNull(viewHolderMatcher);
                this.viewAction = checkNotNull(viewAction);
                this.atPosition = atPosition;
                this.scroller = new ScrollToViewAction<VH>(viewHolderMatcher, atPosition);
            }
            @SuppressWarnings("unchecked")
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isAssignableFrom(RecyclerView.class), isDisplayed());
            }
            @Override
            public PositionableRecyclerViewAction atPosition(int position) {
                checkArgument(position >= 0, "%d is used as an index - must be >= 0", position);
                return new ActionOnItemViewAction<VH>(viewHolderMatcher, viewAction, position);
            }
            @Override
            public String getDescription() {
                if (atPosition == NO_POSITION) {
                    return String.format("performing ViewAction: %s on item matching: %s",
                            viewAction.getDescription(), viewHolderMatcher);
                } else {
                    return String.format("performing ViewAction: %s on %d-th item matching: %s",
                            viewAction.getDescription(), atPosition, viewHolderMatcher);
                }
            }
            @Override
            public void perform(UiController uiController, View root) {
                RecyclerView recyclerView = (RecyclerView) root;
                try {
                    scroller.perform(uiController, root);
                    uiController.loopMainThreadUntilIdle();
                    // the above scroller has checked bounds, dupes (maybe) and brought the element into screen.
                    int max = atPosition == NO_POSITION ? 2 : atPosition + 1;
                    int selectIndex = atPosition == NO_POSITION ? 0 : atPosition;
                    List<MatchedItem> matchedItems = itemsMatching(recyclerView, viewHolderMatcher, max);
                    actionOnItemAtPosition(matchedItems.get(selectIndex).position, viewAction).perform(
                            uiController, root);
                    uiController.loopMainThreadUntilIdle();
                } catch (RuntimeException e) {
                    throw new PerformException.Builder().withActionDescription(this.getDescription())
                            .withViewDescription(HumanReadables.describe(root)).withCause(e).build();
                }
            }
        }

        public static <VH extends RecyclerView.ViewHolder> ViewAction actionOnItemAtPosition(final int position,
                                                                                             final ViewAction viewAction) {
            return new ActionOnItemAtPositionViewAction<VH>(position, viewAction);
        }
        private static final class ActionOnItemAtPositionViewAction<VH extends RecyclerView.ViewHolder> implements
                ViewAction {
            private final int position;
            private final ViewAction viewAction;
            private ActionOnItemAtPositionViewAction(int position, ViewAction viewAction) {
                this.position = position;
                this.viewAction = viewAction;
            }
            @SuppressWarnings("unchecked")
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isAssignableFrom(RecyclerView.class), isDisplayed());
            }
            @Override
            public String getDescription() {
                return "actionOnItemAtPosition performing ViewAction: " + viewAction.getDescription()
                        + " on item at position: " + position;
            }
            @Override
            public void perform(UiController uiController, View view) {
                RecyclerView recyclerView = (RecyclerView) view;
                new ScrollToPositionViewAction(position).perform(uiController, view);
                uiController.loopMainThreadUntilIdle();
                @SuppressWarnings("unchecked")
                VH viewHolderForPosition = (VH) recyclerView.findViewHolderForPosition(position);
                if (null == viewHolderForPosition) {
                    throw new PerformException.Builder().withActionDescription(this.toString())
                            .withViewDescription(HumanReadables.describe(view))
                            .withCause(new IllegalStateException("No view holder at position: " + position))
                            .build();
                }
                View viewAtPosition = viewHolderForPosition.itemView;
                if (null == viewAtPosition) {
                    throw new PerformException.Builder().withActionDescription(this.toString())
                            .withViewDescription(HumanReadables.describe(viewAtPosition))
                            .withCause(new IllegalStateException("No view at position: " + position)).build();
                }
                viewAction.perform(uiController, viewAtPosition);
            }
        }

        private static final class ScrollToViewAction<VH extends RecyclerView.ViewHolder> implements
                PositionableRecyclerViewAction {
            private final Matcher<VH> viewHolderMatcher;
            private final int atPosition;
            private ScrollToViewAction(Matcher<VH> viewHolderMatcher) {
                this(viewHolderMatcher, NO_POSITION);
            }
            private ScrollToViewAction(Matcher<VH> viewHolderMatcher, int atPosition) {
                this.viewHolderMatcher = viewHolderMatcher;
                this.atPosition = atPosition;
            }
            @Override
            public PositionableRecyclerViewAction atPosition(int position) {
                checkArgument(position >= 0, "%d is used as an index - must be >= 0", position);
                return new ScrollToViewAction<VH>(viewHolderMatcher, position);
            }
            @SuppressWarnings("unchecked")
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isAssignableFrom(RecyclerView.class), isDisplayed());
            }
            @Override
            public String getDescription() {
                if (atPosition == NO_POSITION) {
                    return "scroll RecyclerView to: " + viewHolderMatcher;
                } else {
                    return String.format("scroll RecyclerView to the: %dth matching %s.", atPosition,
                            viewHolderMatcher);
                }
            }
            @SuppressWarnings("unchecked")
            @Override
            public void perform(UiController uiController, View view) {
                RecyclerView recyclerView = (RecyclerView) view;
                try {
                    int maxMatches = atPosition == NO_POSITION ? 2 : atPosition + 1;
                    int selectIndex = atPosition == NO_POSITION ? 0 : atPosition;
                    List<MatchedItem> matchedItems = itemsMatching(recyclerView, viewHolderMatcher, maxMatches);
                    if (selectIndex >= matchedItems.size()) {
                        throw new RuntimeException(String.format(
                                "Found %d items matching %s, but position %d was requested.", matchedItems.size(),
                                viewHolderMatcher.toString(), atPosition));
                    }
                    if (atPosition == NO_POSITION && matchedItems.size() == 2) {
                        StringBuilder ambiguousViewError = new StringBuilder();
                        ambiguousViewError.append(
                                String.format("Found more than one sub-view matching %s", viewHolderMatcher));
                        for (MatchedItem item : matchedItems) {
                            ambiguousViewError.append(item + "\n");
                        }
                        throw new RuntimeException(ambiguousViewError.toString());
                    }
                    recyclerView.scrollToPosition(matchedItems.get(selectIndex).position);
                    uiController.loopMainThreadUntilIdle();
                } catch (RuntimeException e) {
                    throw new PerformException.Builder().withActionDescription(this.getDescription())
                            .withViewDescription(HumanReadables.describe(view)).withCause(e).build();
                }
            }
        }

        private static final class ScrollToPositionViewAction implements ViewAction {
            private final int position;
            private ScrollToPositionViewAction(int position) {
                this.position = position;
            }
            @SuppressWarnings("unchecked")
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isAssignableFrom(RecyclerView.class), isDisplayed());
            }
            @Override
            public String getDescription() {
                return "scroll RecyclerView to position: " + position;
            }
            @Override
            public void perform(UiController uiController, View view) {
                RecyclerView recyclerView = (RecyclerView) view;
                recyclerView.scrollToPosition(position);
            }
        }

        @SuppressWarnings("unchecked")
        private static <T extends VH, VH extends RecyclerView.ViewHolder> List<MatchedItem> itemsMatching(
                final RecyclerView recyclerView, final Matcher<VH> viewHolderMatcher, int max) {
            final RecyclerView.Adapter<T> adapter = recyclerView.getAdapter();
            SparseArray<VH> viewHolderCache = new SparseArray<VH>();
            List<MatchedItem> matchedItems = new ArrayList<MatchedItem>();
            for (int position = 0; position < adapter.getItemCount(); position++) {
                int itemType = adapter.getItemViewType(position);
                VH cachedViewHolder = viewHolderCache.get(itemType);
                // Create a view holder per type if not exists
                if (null == cachedViewHolder) {
                    cachedViewHolder = adapter.createViewHolder(recyclerView, itemType);
                    viewHolderCache.put(itemType, cachedViewHolder);
                }
                // Bind data to ViewHolder and apply matcher to view descendants.
                adapter.bindViewHolder((T) cachedViewHolder, position);
                if (viewHolderMatcher.matches(cachedViewHolder)) {
                    matchedItems.add(new MatchedItem(position, HumanReadables.getViewHierarchyErrorMessage(
                            cachedViewHolder.itemView, null,
                            "\n\n*** Matched ViewHolder item at position: " + position + " ***", null)));
                    if (matchedItems.size() == max) {
                        break;
                    }
                }
            }
            return matchedItems;
        }
        private static class MatchedItem {
            public final int position;
            public final String description;
            private MatchedItem(int position, String description) {
                this.position = position;
                this.description = description;
            }
            @Override
            public String toString() {
                return description;
            }
        }

        private static <VH extends RecyclerView.ViewHolder> Matcher<VH> viewHolderMatcher(
                final Matcher<View> itemViewMatcher) {
            return new TypeSafeMatcher<VH>() {
                @Override
                public boolean matchesSafely(RecyclerView.ViewHolder viewHolder) {
                    return itemViewMatcher.matches(viewHolder.itemView);
                }
                @Override
                public void describeTo(Description description) {
                    description.appendText("holder with view: ");
                    itemViewMatcher.describeTo(description);
                }
            };
        }

    }

